import java.util.LinkedList;

public class FlowInNetwork {
    // Nguyễn Đức Đạt - 23000109
    // Thuật toán Ford- Fulkerson
    private int V; // Số đỉnh

    // Tìm kiếm theo chiều rộng
    boolean bfs(int rGraph[][], int s, int t, int parent[]) {
        boolean visited[] = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (!visited[v] && rGraph[u][v] > 0) { // Nếu còn khả năng tăng luồng
                    if (v == t) {
                        parent[v] = u;
                        return true; // Đã tìm thấy đường tăng luồng đến đích
                    }
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return false; // Không tìm thấy đường tăng luồng
    }

    // Giá trị Max Flow từ s đến t trong đồ thị
    public int FordFulkerson(int graph[][], int s, int t) {
        int u, v;

        // Tạo mạng thặng dư dựa trên đồ thị ban đầu
        int rGraph[][] = new int[V][V];
        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }

        int parent[] = new int[V];  // Lưu trữ đường đi từ nguồn đến đích
        int maxFlow = 0;            // Khởi tạo Max Flow bằng 0

        // Tăng luồng khi còn tồn tại đường tăng luồng
        while (bfs(rGraph, s, t, parent)) {
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            // Cập nhật lại Flow trên mạng thặng dư
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
            }

            // Cộng thêm Flow của đường vừa tìm được vào Max Flow
            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}
