using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Orka.Backend.DataObjects
{
    public class Trainer
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public List<int> Teams { get; set; }
    }
}