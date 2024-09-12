import type { MonthName } from "../types/date_types";

export const monthDays: Record<MonthName, number> = {
  "January": 31,
  "February": 29,  // Leap year February has 29 days
  "March": 31,
  "April": 30,
  "May": 31,
  "June": 30,
  "July": 31,
  "August": 31,
  "September": 30,
  "October": 31,
  "November": 30,
  "December": 31
};