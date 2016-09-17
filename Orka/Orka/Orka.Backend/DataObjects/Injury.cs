using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Orka.Backend.DataObjects
{
    public class Injury
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public int BodyPart { get; set; }
        public int Severity { get; set; }
        public int Days { get; set; }
    }
}