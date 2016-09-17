using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Orka.Backend.Controllers
{
    public class AthleteController : ApiController
    {
        // GET: api/Athlete
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET: api/Athlete/5
        public string Get(int id)
        {
            return "value";
        }

        // POST: api/Athlete
        public void Post([FromBody]string value)
        {
        }

        // PUT: api/Athlete/5
        public void Put(int id, [FromBody]string value)
        {
        }

        // PATCH tables/TodoItem/48D68C86-6EA6-4C25-AA33-223FC9A27959
        public Task<TodoItem> PatchTodoItem(string id, Delta<TodoItem> patch)
        {
            return UpdateAsync(id, patch);
        }

        // DELETE: api/Athlete/5
        public void Delete(int id)
        {
        }
    }
}
