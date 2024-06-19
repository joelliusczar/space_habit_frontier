using Microsoft.AspNetCore.Mvc;
using SpaceHabitFrontier.Services;
using SpaceHabitFrontier.Dtos;

namespace SpaceHabitFrontier.Controllers;

[ApiController]
[Route("api/[controller]")]
public class DailiesController : ControllerBase
{

    private readonly ILogger<DailiesController> _logger;

    public DailiesController(
			ILogger<DailiesController> logger,
			LookupsService lookupsService
		)
    {
        _logger = logger;
    }

		[HttpPost(Name = "Add")]
    public int Post([FromForm]DailyFormDto formDto)
    {
			return 17;
    }

}
