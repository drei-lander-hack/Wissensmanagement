using HackathonBackEnd.Services;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace HackathonBackEnd.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly GraphService graphService;

        public UsersController(GraphService graphService)
        {
            this.graphService = graphService;
        }

        // GET api/values
        [HttpGet]
        public async Task<IEnumerable<UserListItem>> Get(string value) => (await graphService
            .GetUserListItems())
            .Where(user =>
            {
                if (string.IsNullOrWhiteSpace(value)) return true;
                if (user.Name?.StartsWith(value) ?? false) return true;
                if (user.Projects.Any(project => project.Contains(value))) return true;
                if (user.Skills.Any(skill => skill.Contains(value))) return true;
                return false;
            });
    }
}
