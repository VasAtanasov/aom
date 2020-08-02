package bg.autohouse.service.scheduling;

import bg.autohouse.data.repositories.OfferRepository;
import bg.autohouse.util.TimeUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ScheduledOfferDeactivation {

  private final OfferRepository offerRepository;

  @Scheduled(cron = "0 0 2 * * *") // runs at 2am UTC every day
  public void deactivateOffers() {
    Date today = TimeUtils.now();
    Calendar cal = new GregorianCalendar();
    cal.setTime(today);
    cal.add(Calendar.DAY_OF_MONTH, -30);
    Date today30 = cal.getTime();
    offerRepository.setInactiveOffersBefore(today30);
  }
}
