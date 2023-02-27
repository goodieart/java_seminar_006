import java.util.*;
import java.awt.Point;

/**
 * Реализация алгоритма Ли для поиска кратчайшего пути между двумя точками сетки.
 */
public class LeeAlgorithm {
    private final int[][] grid;
    private final int height;
    private final int width;
    private final int[] dx = {0, 0, 1, -1};
    private final int[] dy = {1, -1, 0, 0};

    /**
     * Создает новый экземпляр класса LeeAlgorithm с указанной сеткой.
     *
     * @param grid сетка для поиска пути
     */
    public LeeAlgorithm(int[][] grid) {
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;
    }

    /**
     * Находит кратчайший путь между указанными начальной и конечной точками, используя алгоритм Ли.
     *
     * @param start начальная точка пути
     * @param end конечная точка пути
     * @return список точек, представляющих кратчайший путь, или нуль, если путь не найден
     */
    public List<Point> findShortestPath(Point start, Point end) {
        int[][] distances = new int[height][width];
        boolean[][] visited = new boolean[height][width];
        Queue<Point> queue = new LinkedList<>();

        queue.offer(start);
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) {
                return buildPath(distances, start, end);
            }

            for (int i = 0; i < 4; i++) {
                int nextX = current.x + dx[i];
                int nextY = current.y + dy[i];

                if (nextX >= 0 && nextX < height && nextY >= 0 && nextY < width
                        && !visited[nextX][nextY] && grid[nextX][nextY] != 1) {
                    queue.offer(new Point(nextX, nextY));
                    visited[nextX][nextY] = true;
                    distances[nextX][nextY] = distances[current.x][current.y] + 1;
                }
            }
        }

        return null;
    }

    /**
     * Строит кратчайший путь между указанными начальной и конечной точками, используя расстояния, рассчитанные алгоритмом Ли.
     *
     * @param distances расстояния от каждой точки до начальной точки
     * @param start начальная точка пути
     * @param end конечная точка пути
     * @return список точек, представляющих кратчайший путь
     */
    private List<Point> buildPath(int[][] distances, Point start, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;

        while (!current.equals(start)) {
            path.add(current);
            int currentDist = distances[current.x][current.y];

            for (int i = 0; i < 4; i++) {
                int nextX = current.x + dx[i];
                int nextY = current.y + dy[i];

                if (nextX >= 0 && nextX < height && nextY >= 0 && nextY < width
                        && distances[nextX][nextY] == currentDist - 1) {
                    current = new Point(nextX, nextY);
                    break;
                }
            }
        }

        path.add(start);
        Collections.reverse(path);
        return path;
    }
}
