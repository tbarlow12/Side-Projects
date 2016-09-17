using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Orka.Backend.DataObjects
{
    public class Athlete
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public DateTime Birthdate { get; set; }
        public string InsurancePolicy { get; set; }
        public List<int> Teams { get; set; }
    }
}