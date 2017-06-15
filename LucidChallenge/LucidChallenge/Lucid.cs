using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LucidChallenge
{
    static class Lucid
    {
        static void Main(string[] args)
        {
            var grid = ReadGrid();
            Console.WriteLine(FindSubSequence(grid));
        }

        private static int FindSubSequence(int[][] grid)
        {
            var queue = new Queue<int[]>();
            int longest_path_length = 0;
            for (int row = 0; row < grid.Length; row++)
                for (int col = 0; col < grid[row].Length; col++)
                {
                    var first = new int[] { row, col };
                    HashSet<int[]> visited = new HashSet<int[]> { first };
                    queue.Enqueue(first);
                    int path_length = 0;
                    while(queue.Count != 0)
                    {
                        var coords = queue.Dequeue();
                        queue.TryAddNeighbor(visited, grid, grid[row][col], coords[0] - 1, coords[1], 3);
                        queue.TryAddNeighbor(visited, grid, grid[row][col], coords[0] + 1, coords[1], 3);
                        queue.TryAddNeighbor(visited, grid, grid[row][col], coords[0], coords[1] - 1, 3);
                        queue.TryAddNeighbor(visited, grid, grid[row][col], coords[0], coords[1] + 1, 3);
                        path_length++;
                    }
                    if (path_length > longest_path_length)
                        longest_path_length = path_length;
                }
            return longest_path_length;
        }

        private static void TryAddNeighbor(this Queue<int[]> queue, HashSet<int[]> visited, int[][] grid, int original_value, int row, int col, int minDiff)
        {
            if ((row >= 0 && row < grid.Length) && (col >= 0 && col < grid[row].Length))
            {
                if (Math.Abs(grid[row][col] - original_value) >= minDiff)
                {
                    var coords = new int[] { row, col };
                    if (!visited.ValContains(coords))
                    {
                        queue.Enqueue(coords);
                        visited.Add(coords);
                    }
                }
            }
        }

        private static bool ValContains(this HashSet<int[]> set, int[] item)
        {
            foreach (var set_item in set)
            {
                if (Enumerable.SequenceEqual(set_item, item))
                    return true;
            }
            return false;
        }


        private static int[][] ReadGrid()
        {
            List<int[]> lines = new List<int[]>();
            Console.ReadLine(); //skip the first line
            string line;
            while((line = Console.ReadLine()) != null && line != "")
            {
                int[] rowOfNumbers = Array.ConvertAll(line.Split(), s=> int.Parse(s));
                lines.Add(rowOfNumbers);
            }
            return lines.ToArray();
        }
    }

}
