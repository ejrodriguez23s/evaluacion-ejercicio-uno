package ec.evaluacion.ejercicio.uno.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Class representing a generic directed graph.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
@Lazy
@Component
public class Graph<T> {

	private Map<T, GraphNode> graph = new HashMap<>();

	/**
	 * Add a new node to the graph.
	 * 
	 * @param node
	 * @author erodriguez on 2019/3/29.
	 */
	public void addNode(T node) {
		GraphNode graphNode = new GraphNode(node);
		if (!graph.containsKey(node)) {
			graph.put(node, graphNode);
		}
	}

	/**
	 * Check whether graphs contains given node.
	 * 
	 * @param node
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	public Boolean contains(T node) {
		return graph.containsKey(node);
	}

	/**
	 * Add an unidirectional edge from a given start node to a destination node.
	 * 
	 * @param start  Add an unidirectional edge from a given start node to a
	 *               destination node.
	 * @param dest   the node with the incoming edge.
	 * @param weight
	 * @author erodriguez on 2019/3/29.
	 */
	public void addEdge(T start, T dest, int weight) {
		validateInputNodes(start, dest);

		if (weight < 0) {
			throw new IllegalArgumentException("Weight must be >= 0");
		}
		graph.get(start).getNeighbours().put(dest, weight);
	}

	/**
	 * Get all the adjacent graph of the given node.
	 * @param node
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	public Set<T> getNeighbours(T node) {
		validateInputNodes(node);
		return graph.get(node).getNeighbours().keySet();
	}

	/**
	 * 
	 * @param start  the start node.
	 * @param dest The destination node.
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	public Boolean edgeExists(T start, T dest) {
		validateInputNodes(start, dest);

		return graph.get(start).getNeighbours().containsKey(dest);
	}

	/**
	 * Get weight of the given edge. 
	 * 
	 * @param start
	 * @param dest
	 * @return
	 * 
	 * @author erodriguez on 2019/3/29.
	 */
	private int weightForEdge(T start, T dest) {
		return graph.get(start).getNeighbours().get(dest);
	}


	/**
	 * Run a Dijkstra
	 *
	 * @param start source node.
	 * @param dest  destination node.
	 * @author erodriguez on 2019/3/29.
	 */
	private void dijkstra(T start, T dest) {
		PriorityQueue<GraphNode> queue = new PriorityQueue<GraphNode>();

		Set<T> nodes = graph.keySet();
		for (T node : nodes) {
			GraphNode graphNode = graph.get(node);
			graphNode.setDistance(Integer.MAX_VALUE);
			if (start.equals(node)) {
				graphNode.setDistance(0);
				graphNode.setPrevious(graphNode);
			}

			// Add node to priority queue
			queue.add(graphNode);
		}

		// Compute distances
		while (!queue.isEmpty()) {
			GraphNode currentGraphNode = queue.poll(); // Node with shortest distance

			if (currentGraphNode.getData().equals(dest) && !currentGraphNode.equals(currentGraphNode.getPrevious())) {
				break;
			}

			for (T neighbour : getNeighbours(currentGraphNode.getData())) {
				GraphNode neighbourGraphNode = graph.get(neighbour);

				final int alternateDist = currentGraphNode.getDistance()
						+ weightForEdge(currentGraphNode.getData(), neighbour);
				if (alternateDist < neighbourGraphNode.getDistance()
						|| neighbourGraphNode.equals(neighbourGraphNode.getPrevious())) { // shorter path to neighbour
																							// found
					queue.remove(neighbourGraphNode);
					neighbourGraphNode.setDistance(alternateDist);
					neighbourGraphNode.setPrevious(currentGraphNode);
					queue.add(neighbourGraphNode);
				}
			}
		}
	}

	/**
	 * 
	 * @param start the start node.
	 * @param stop
	 * @param filter
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	private int countPaths(T start, DFSCondition stop, DFSCondition filter) {
		int total = 0;

		// Start a DFS from each neighbour
		for (T neighbour : getNeighbours(start)) {
			GraphPath path = new GraphPath();
			path.appendNode(start, 0);
			path.appendNode(neighbour, weightForEdge(start, neighbour));
			total += dfs(neighbour, stop, filter, path);
		}

		return total;
	}

	/**
	 * 
	 * @param currentNode
	 * @param stop
	 * @param filter
	 * @param path
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	private int dfs(T currentNode, DFSCondition stop, DFSCondition filter, GraphPath path) {
		int total = 0;

		if (filter.evaluate(path)) {
			// Count path as valid
			total++;
		}

		for (T neighbour : getNeighbours(currentNode)) {

			path.appendNode(neighbour, weightForEdge(currentNode, neighbour));

			if (stop.evaluate(path)) {
				path.removeLastNode(weightForEdge(currentNode, neighbour));
				continue; // go to next neighbour
			} else {
				total += dfs(neighbour, stop, filter, path);
			}

			path.removeLastNode(weightForEdge(currentNode, neighbour));
		}
		return total;
	}

	/**
	 * 
	 * @param nodes
	 * @author erodriguez on 2019/3/29.
	 * 
	 */
	private void validateInputNodes(T... nodes) {
		for (T node : nodes) {
			if (!contains(node)) {
				throw new TrainException("Elementos incorrectos");
			}
		}
	}

	/**
	 * 
	 * @param start
	 * @param dest
	 * @return
	 * @throws TrainException
	 * @author erodriguez on 2019/3/29.
	 */
	public GraphPath shortestPathBetween(T start, T dest) throws TrainException {
		validateInputNodes(start, dest);

		dijkstra(start, dest);

		GraphNode destNode = graph.get(dest);
		GraphPath path = destNode.path(graph.get(start));

		if (path.getNodes().isEmpty()) {
			throw new TrainException("Nodos vacios");
		} else {
			return path;
		}
	}


	/**
	 * Get total distance of route.
	 * 
	 * @param nodes
	 * @return
	 * @throws TrainException
	 * @author erodriguez on 2019/3/29.
	 */
	public int distance(List<T> nodes) throws TrainException {
		validateInputNodes((T[]) nodes.toArray());

		int distance = 0;
		for (int i = 0; i < nodes.size() - 1; i++) {
			T start = nodes.get(i);
			T dest = nodes.get(i + 1);

			if (edgeExists(start, dest)) {
				distance += weightForEdge(start, dest);
			} else {
				throw new TrainException("no such rout");
			}
		}
		return distance;
	}


	/**
	 *Count number of possible routes starting from start and ending at dest with a
	 * maximum number of maxHops.
	 * @param start the start node.
	 * @param dest the destination node.
	 * @param maxHops number of stops in route.
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	public int countRoutesWithMaxHops(T start, T dest, int maxHops) {
		validateInputNodes(start, dest);

		return countPaths(start, (p) -> {
			return p.hopCount() > maxHops;
		}, (p) -> {
			return dest.equals(p.last());
		});
	}

	/**
	 * Count number of possible routes starting from start and ending at dest with a
	 * exactly number of hops.
	 *
	 * @param start starting node of route.
	 * @param dest  destination node of route.
	 * @param hops  number of stops in route.
	 * @return number of possible routes.
	 * @author erodriguez on 2019/3/29.
	 */
	public int countRoutesWithHops(T start, T dest, int hops) {
		validateInputNodes(start, dest);

		return countPaths(start, (p) -> {
			return p.hopCount() > hops;
		}, (p) -> {
			return dest.equals(p.last()) && p.hopCount() == hops;
		});
	}

	/**
	 * Count number of possible routes starting from start and ending at dest with a
	 * distance less equal to distance.
	 *
	 * @param start    starting node of route.
	 * @param dest     destination node of route.
	 * @param distance maximum allowed distance.
	 * @return number of possible routes.
	 * @author erodriguez on 2019/3/29.
	 */
	public int countRoutesWithMaxDistance(T start, T dest, int distance) {
		validateInputNodes(start, dest);

		return countPaths(start, (p) -> {
			return p.distance() > distance;
		}, (p) -> {
			return dest.equals(p.last()) && p.distance() <= distance;
		});
	}

	/**
	 * Internal Graph Node model never exposed to the public. Wrapper class
	 * containing properties for shortest path computation, etc.
	 * 
	 * @author erodriguez on 2019/3/29.
	 */
	private class GraphNode implements Comparable<GraphNode> {
		private T data;
		private Integer distance;
		private GraphNode previous;
		protected Map<T, Integer> neighbours;

		public GraphNode(T data) {
			this.data = data;
			this.neighbours = new HashMap<T, Integer>();
		}

		public T getData() {
			return data;
		}

		public Map<T, Integer> getNeighbours() {
			return neighbours;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		public GraphNode getPrevious() {
			return previous;
		}

		public void setPrevious(GraphNode previous) {
			this.previous = previous;
		}

		public GraphPath path(GraphNode startNode) {
			List<T> nodes = new LinkedList<T>();
			int distance = 0;
			GraphNode currentNode = this;

			if (currentNode.getPrevious() == null) {
				return new GraphPath(nodes, distance);
			} else {
				nodes.add(currentNode.getData());

				while (!currentNode.equals(currentNode.getPrevious())) {
					distance += weightForEdge(currentNode.getPrevious().getData(), currentNode.getData());

					currentNode = currentNode.getPrevious();
					nodes.add(0, currentNode.getData());

					if (currentNode.equals(startNode)) {
						break;
					}
				}

				return new GraphPath(nodes, distance);
			}
		}

		public int compareTo(GraphNode other) {
			return distance.compareTo(other.getDistance());
		}

		@Override
		public String toString() {
			return data.toString();
		}
	}

	/**
	 * Represents a path containing the nodes in
	 * the order in which they have been traversed.
	 *  @author erodriguez on 2019/3/29.
	 */
	public class GraphPath {
		private final String PATH_SEPARATOR = "->";
		private List<T> nodes;
		private int distance;

		/**
		 * Creates a new empty
		 */
		public GraphPath() {
			this.nodes = new ArrayList<T>();
			this.distance = 0;
		}

		/**
		 * Creates a new graphPath with the provided nodes and length.
		 *  
		 * @param nodes
		 * @param length
		 * @author erodriguez on 2019/3/29.
		 */
		public GraphPath(List<T> nodes, int length) {
			this.nodes = nodes;
			this.distance = length;
		}

		/**
		 * Remove last node of the path.
		 * 
		 * @param weight
		 * @return
		 * @author erodriguez on 2019/3/29.
		 */
		public boolean removeLastNode(int weight) {
			if (!this.nodes.isEmpty()) {
				distance -= weight;
				this.nodes.remove(this.nodes.size() - 1);
				return true;
			} else {
				return false;
			}
		}


		/**
		 * Append node to the end of the path.
		 * 
		 * @param node
		 * @param weight
		 * @return
		 * @author erodriguez on 2019/3/29.
		 */
		public boolean appendNode(T node, int weight) {
			distance += weight;
			return this.nodes.add(node);
		}

		/**
		 * List of nodes.
		 *
		 * @return List of nodes.
		 */
		public List<T> getNodes() {
			return nodes;
		}

		/**
		 * 
		 * @return
		 */
		public T last() {
			if (nodes.isEmpty()) {
				return null;
			} else {
				return nodes.get(nodes.size() - 1);
			}
		}

		/**
		 * Total length of path. This is the sum of edge weights.
		 *
		 * @return the length of the path.
		 */
		public int distance() {
			return distance;
		}

	
		/**
		 * Total length of path in terms of number of hops.
		 * @return
		 */
		public int hopCount() {
			return Math.max(0, this.nodes.size() - 1);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < nodes.size() - 1; i++) {
				sb.append(nodes.get(i) + PATH_SEPARATOR);
			}
			sb.append(nodes.get(nodes.size() - 1));
			return sb.toString();
		}
	}

	/**
	 * Condition to be evaluated for a path during DFS traversal
	 */
	private interface DFSCondition {
		/**
		 * Evaluate condition for given path.
		 *
		 * @param path Path for which to evaluate condition
		 * @return true if condition is met. False otherwise
		 */
		@SuppressWarnings("rawtypes")
		boolean evaluate(Graph.GraphPath path);
	}

}
