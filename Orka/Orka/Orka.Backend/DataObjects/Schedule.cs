using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Orka.Backend.DataObjects
{
    public class Schedule
    {
        public int TeamId { get; set; }
        public int Year { get; set; }
        public List<Game> Games { get; set; }
    }

    public class Game
    {
        public DateTime Date { get; set; }
        public string Opponent { get; set; }
    }
}