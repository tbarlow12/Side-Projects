using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
//using MySql.Data.MySqlClient;

namespace Scraper
{
    class DatabaseConnection
    {
        string username = "cpuofu";
        string password = "ut@ahman1855";
        string connectionString;
        private SqlConnection connection;
        bool connected;

        public bool Connect()
        {
            connectionString = string.Format("Server=tcp:cpuofu.database.windows.net,1433;Data Source=cpuofu.database.windows.net;Initial Catalog=CPUofU_CoursePlanner_SQL;Persist Security Info=False;User ID={your_username};Password={your_password};MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;", username, password);

            return Connect(connectionString);
        }

        public bool Connect(string connectionString)
        {
            if (String.IsNullOrEmpty(connectionString))
                return false;
            connection = new SqlConnection(connectionString);
            connection.Open();
            connected = true;
            return connected;
        }

        public void Close()
        {
            connection.Close();
        }


        public void ExecuteUpdate(string sql)
        {
            if (connected && sql.Length > 0)
            {
                var cmd = new SqlCommand(sql, connection);
                cmd.ExecuteNonQuery();
            }
        }

        public SqlDataReader ExecuteQuery(string sql)
        {
            if (connected)
            {
                var cmd = new SqlCommand(sql, connection);
                return cmd.ExecuteReader();
            }
            return null;
        }
    }
}
