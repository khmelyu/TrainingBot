package trainingBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class Calendar {
    private final Map<Long, LocalDate> currentDisplayedMonths = new HashMap<>();

    public InlineKeyboardMarkup createCalendar(LocalDate selectedDate, long id) {

            currentDisplayedMonths.put(id, selectedDate);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<InlineKeyboardButton> yearRow = new ArrayList<>();
        yearRow.add(InlineKeyboardButton.builder().text(String.valueOf(selectedDate.getYear())).callbackData("IGNORE").build());
        keyboard.add(yearRow);

        List<InlineKeyboardButton> navigationRow = new ArrayList<>();
        navigationRow.add(InlineKeyboardButton.builder().text("◀️").callbackData("PREV_MONTH").build());
        navigationRow.add(InlineKeyboardButton.builder().text(selectedDate.format(DateTimeFormatter.ofPattern("LLLL", new Locale("ru")))).callbackData("IGNORE").build());
        navigationRow.add(InlineKeyboardButton.builder().text("▶️").callbackData("NEXT_MONTH").build());
        keyboard.add(navigationRow);

        List<InlineKeyboardButton> daysOfWeekRow = new ArrayList<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            String dayName = dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, new Locale("ru"));
            daysOfWeekRow.add(InlineKeyboardButton.builder().text(dayName).callbackData("IGNORE").build());
        }
        keyboard.add(daysOfWeekRow);

        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();
        int firstDayOfWeek = yearMonth.atDay(1).getDayOfWeek().getValue();

        int dayCounter = 1;
        List<InlineKeyboardButton> row = new ArrayList<>();

        for (int i = 1; i < firstDayOfWeek; i++) {
            row.add(InlineKeyboardButton.builder().text(" ").callbackData("IGNORE").build());
        }

        for (int i = firstDayOfWeek; i <= 7; i++) {
            LocalDate date = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), dayCounter);
            String callbackData = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
            row.add(InlineKeyboardButton.builder().text(String.valueOf(dayCounter)).callbackData(callbackData).build());
            dayCounter++;
        }
        keyboard.add(row);

        while (dayCounter <= daysInMonth) {
            row = new ArrayList<>();
            for (int i = 1; i <= 7 && dayCounter <= daysInMonth; i++) {
                LocalDate date = LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), dayCounter);
                String callbackData = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
                row.add(InlineKeyboardButton.builder().text(String.valueOf(dayCounter)).callbackData(callbackData).build());
                dayCounter++;
            }
            keyboard.add(row);
        }

        int remainingDays = 7 - keyboard.get(keyboard.size() - 1).size();
        for (int i = 0; i < remainingDays; i++) {
            keyboard.get(keyboard.size() - 1).add(InlineKeyboardButton.builder().text(" ").callbackData("IGNORE").build());
        }

        List<InlineKeyboardButton> back = new ArrayList<>();
        back.add(InlineKeyboardButton.builder().text("Назад").callbackData("BACK").build());
        keyboard.add(back);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup changeMonth(String callbackData, long id) {
        LocalDate displayedMonth = currentDisplayedMonths.get(id);

        if (displayedMonth == null) {
            displayedMonth = LocalDate.now();
            currentDisplayedMonths.put(id, displayedMonth);
        }

        if (callbackData.equals("PREV_MONTH")) {
            displayedMonth = displayedMonth.minusMonths(1);
        } else if (callbackData.equals("NEXT_MONTH")) {
            displayedMonth = displayedMonth.plusMonths(1);
        }

        currentDisplayedMonths.put(id, displayedMonth);

        return createCalendar(displayedMonth, id);
    }
}
