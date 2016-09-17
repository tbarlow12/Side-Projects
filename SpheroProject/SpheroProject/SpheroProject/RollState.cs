using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SpheroProject
{
    class RollState
    {
        public RollState()
        {
            Heading = 0;
            Speed = 1f;
        }

        public int Heading { get; set; }
        public float Speed { get; set; }

        public void Flip()
        {
            if (Heading == 180)
                Heading = 0;
            else
                Heading = 180;
        }
    }
}
