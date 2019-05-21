using System;

namespace HackathonBackEnd.Services
{
    public class UserListItem
    {
        public string Name { get; set; }

        public string[] Skills { get; set; }

        public string[] Projects { get; set; }

        public string Education { get; set; }

        public string Company { get; set; }

        public string[] BearbeiteteDokumente { get; set; }

        public DateTime ErsterEintrag { get; set; }

        public double Rank { get; set; }

        public string Image { get; set; }
    }
}