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
                var terms = value.Split(' ');
                if (string.IsNullOrWhiteSpace(value)) return true;

                return
                    Contains(user.Name, terms) ||
                    Contains(user.Projects, terms) ||
                    Contains(user.Skills, terms);
            })
            .OrderByDescending(user => user.Rank);

        private static bool Contains(string word, IEnumerable<string> terms)
        {
            return Contains(new string[] { word }, terms);
        }

        private static bool Contains(IEnumerable<string> words, IEnumerable<string> terms)
        {
            return terms.All(term => words.Any(word => word != null && word.Contains(term, System.StringComparison.OrdinalIgnoreCase)));
        }
    }
}
