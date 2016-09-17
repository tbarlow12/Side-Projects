namespace GPS_Service.Migrations
{
    using Models;
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Migrations;
    using System.Linq;

    internal sealed class Configuration : DbMigrationsConfiguration<GPS_Service.Models.GPS_ServiceContext>
    {
        public Configuration()
        {
            AutomaticMigrationsEnabled = false;
        }

        protected override void Seed(GPS_Service.Models.GPS_ServiceContext context)
        {
            context.Courses.AddOrUpdate(p => p.Name,
                new Course
                {
                    Name = "Algorithms",
                    Professor = "Joe Zachary",
                    Catalog = 4150
                },
                new Course
                {
                    Name = "Prob and Stats",
                    Professor = "Neil Cotter",
                    Catalog = 3130
                },
                new Course
                {
                    Name = "Software Practice II",
                    Professor = "David Johnson",
                    Catalog = 3505
                },
                new Course
                {
                    Name = "Database Systems",
                    Professor = "Feifei Li",
                    Catalog = 5530
                },
                new Course
                {
                    Name = "Industry Forum",
                    Professor = "Kobus Van de Mere",
                    Catalog = 3100
                }
                );
        }
    }
}
