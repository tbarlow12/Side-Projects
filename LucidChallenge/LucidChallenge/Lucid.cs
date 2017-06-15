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
            var longest_path = FindLongestPath(grid);
        }

        private static int FindLongestPath(int[][] grid)
        {
            for(int i = 0; i < grid.Length; i++)
            {
                for (int j = 0; j < grid[i].Length; j++)
                {
                    Stack<int[]> stack = new Stack<int[]>();
                    stack.Push(new int[] { i, j });
                    List<Tuple<int, int>> explored = new List<Tuple<int, int>>();
                    var longest_path = 0;
                    var path_length = 1;
                    while(stack.Count > 0)
                    {
                        var coords = stack.Peek();
                        int row = coords[0];
                        int col = coords[1];

                        int original_count = stack.Count;
                        //push neighbors if they satisfy requirement
                        stack.PushIfSatisfy(grid[row][col], row - 1, col - 1, grid);
                        stack.PushIfSatisfy(grid[row][col], row - 1, col, grid);
                        stack.PushIfSatisfy(grid[row][col], row - 1, col + 1, grid);
                        stack.PushIfSatisfy(grid[row][col], row, col - 1, grid);
                        stack.PushIfSatisfy(grid[row][col], row, col + 1, grid);
                        stack.PushIfSatisfy(grid[row][col], row + 1, col - 1, grid);
                        stack.PushIfSatisfy(grid[row][col], row + 1, col, grid);
                        stack.PushIfSatisfy(grid[row][col], row + 1, col + 1, grid);
                        if (stack.Count > original_count)
                            path_length++;
                        else
                        {
                            stack.Pop();
                            longest_path = Math.Max(longest_path, path_length);
                            path_length--;
                        }
                        //record biggest length so far
                    }
                }
            }
            return 0;
        }

        private static bool PushIfSatisfy(this Stack<int[]> stack,int val, int row, int col, int[][] grid)
        {
            var coords = new int[] { row, col };
            if (row >= 0 && row < grid.Length && col >= 0 && col < grid[row].Length && Math.Abs(val - grid[row][col]) >= 3)
            { 
                stack.Push(coords);
                return true;
            }
            return false;
        }
        private static bool HasPath(this List<List<int>> paths, List<int> path, int newVal)
        {
            foreach(List<int> p in paths)
            {
                List<int> temp = p.ToList();
                temp.Add(newVal);
                if (temp.SequenceEqual(path)) return true;
            }
            return false;
        }

        private static int[][] ReadGrid()
        {
            List<int[]> lines = new List<int[]>();
            Console.ReadLine(); //skip the first line
            string line;
            while((line = Console.ReadLine()) != null && line != "")
                lines.Add(Array.ConvertAll(line.Split(), s=> int.Parse(s)));
            return lines.ToArray();
        }
    }
    class Node<T>
    {
        public List<Node<T>> Nodes { get; set; }
        public T Value { get; set; }
    }

}
