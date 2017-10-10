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
            Console.WriteLine(FindLongestPath(grid));
        }

        private static int FindLongestPath(int[][] grid)
        {
            int longest_path = 0;
            for(int i = 0; i < grid.Length; i++)
            {
                for (int j = 0; j < grid[i].Length; j++)
                {
                    Node node = new Node(null, grid[i][j]);
                    node.AddChildren(grid, i, j);
                    longest_path = Math.Max(longest_path, node.LongestPath);
                    int k = 0;
                }
            }
            return longest_path;
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
    class Node
    {
        public Node Parent { get; set; }
        public List<Node> Children { get; set; }
        public int Value { get; set; }
        public Node(Node parent, int val)
        {
            Parent = parent;
            Value = val;
            Children = new List<Node>();
        }
        internal void AddChildren(int[][] grid, int i, int j)
        {
            TryAddChild(grid, i - 1, j - 1);
            TryAddChild(grid, i - 1, j);
            TryAddChild(grid, i - 1, j + 1);
            TryAddChild(grid, i, j - 1);
            TryAddChild(grid, i, j + 1);
            TryAddChild(grid, i + 1, j - 1);
            TryAddChild(grid, i + 1, j);
            TryAddChild(grid, i + 1, j + 1);
        }
        public int LongestPath {
            get
            {
                if (Children.Count == 0)
                    return 1;
                List<int> maxs = new List<int>();
                foreach (var child in Children)
                    maxs.Add(child.LongestPath);
                return 1 + maxs.Max();
            }
        }

        private void TryAddChild(int[][] grid, int i, int j)
        {
            if (!(i >= 0 && j >= 0 && i < grid.Length && j < grid[i].Length && Math.Abs(Value - grid[i][j]) >= 3))
                return;
            Node tempParent = this.Parent;
            while (tempParent != null)
            {
                if (tempParent.Value == grid[i][j])
                    return;
                tempParent = tempParent.Parent;
            }
            Node child = new Node(this,grid[i][j]);
            child.AddChildren(grid, i, j);
            Children.Add(child);
        }
    }
}
