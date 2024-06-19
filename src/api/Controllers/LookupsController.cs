using Microsoft.AspNetCore.Mvc;
using SpaceHabitFrontier.Services;
using SpaceHabitFrontier.Dtos;

namespace SpaceHabitFrontier.Controllers;

[ApiController]
[Route("api/[controller]")]
public class LookupsController : ControllerBase
{

    private readonly ILogger<LookupsController> _logger;
		private readonly LookupsService _lookupsService;

    public LookupsController(
			ILogger<LookupsController> logger,
			LookupsService lookupsService
		)
    {
        _logger = logger;
				_lookupsService = lookupsService;
    }

		[HttpGet(Name = "Lookups")]
    public LookupsDto Get()
    {
			return _lookupsService.GetLookups();
    }

}
