using System.Reflection;
using SpaceHabitFrontier.Dtos;
namespace SpaceHabitFrontier.Extensions;

[AttributeUsage(AttributeTargets.Field)]
public sealed class StringValueAttribute: Attribute
{
	public StringValueAttribute(string value)
	{
		Value = value;
	}

	public string Value { get; set; }
}

public static class EnumExtensions
{
	public static string StringValue<T>(this T value) where T: Enum
	{
		var fieldName = value.ToString();
		var field = typeof(T)
			.GetField(fieldName, BindingFlags.Public | BindingFlags.Static);
		return field?
			.GetCustomAttribute<StringValueAttribute>()?.Value ?? fieldName;
	}

	public static IEnumerable<NamedIdDto> ToEnumerable<T>() where T: struct, Enum
	{
		return Enum.GetValues<T>().Select(e => new NamedIdDto
		{
			Id = Convert.ToInt32(e),
			Name = e.StringValue()
		});
		
	}
}