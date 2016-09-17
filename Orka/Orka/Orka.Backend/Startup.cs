using Microsoft.Owin;
using Owin;

[assembly: OwinStartup(typeof(Orka.Backend.Startup))]

namespace Orka.Backend
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureMobileApp(app);
        }
    }
}