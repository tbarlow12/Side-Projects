using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Orka.Backend.DataObjects
{
    public class Sustains
    {
        public int AthleteId { get; set; }
        public int InjuryId { get; set; }
        public DateTime Date { get; set; }
        public string Cause { get; set; }
        public int Days { get; set; }
    }
}