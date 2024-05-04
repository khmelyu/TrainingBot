package trainingBot.service.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingBot.model.entity.Gallery;
import trainingBot.model.entity.Office;
import trainingBot.model.rep.GalleryRepository;
import trainingBot.model.rep.OfficeRepository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.util.List;

@Service
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class ContactSearchAction {
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final OfficeRepository officeRepository;
    private final GalleryRepository galleryRepository;

    @Value("${contact.search.no.message}")
    private String contactSearchNoMessage;
    @Value("${contact.search.yes.message}")
    private String contactSearchYesMessage;
    @Value("${contact.search.product.quality}")
    private String contactSearchProductQuality;
    @Value("${contact.search.promotion}")
    private String contactSearchPromotion;
    @Value("${contact.search.tech}")
    private String contactSearchTech;
    @Value("${contact.search.chain.meeting}")
    private String contactSearchChainMeeting;
    @Value("${contact.search.takeaway}")
    private String contactSearchTakeaway;
    @Value("${contact.search.order}")
    private String contactSearchOrder;
    @Value("${contact.search.sick.leave.request}")
    private String contactSearchSickLeaveRequest;
    @Value("${contact.search.contract}")
    private String contactSearchContract;
    @Value("${contact.search.staffer}")
    private String contactSearchStaffer;
    @Value("${contact.search.staffer.empty}")
    private String contactSearchStafferEmpty;
    @Value("${contact.search.gallery}")
    private String contactSearchGallery;
    @Value("${contact.search.gallery.empty}")
    private String contactSearchGalleryEmpty;


    @Autowired
    public ContactSearchAction(Sendler sendler, UserStateService userStateService, OfficeRepository officeRepository, GalleryRepository galleryRepository) {
        this.sendler = sendler;
        this.userStateService = userStateService;
        this.officeRepository = officeRepository;
        this.galleryRepository = galleryRepository;
    }

    public void noMenu(long id) {
        sendler.sendContactSearchNoMenu(id, contactSearchNoMessage);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH_NO);
    }

    public void productQuality(long id) {
        sendler.sendTextMessage(id, contactSearchProductQuality);
    }

    public void promotion(long id) {
        sendler.sendTextMessage(id, contactSearchPromotion);
    }

    public void tech(long id) {
        sendler.sendTextMessage(id, contactSearchTech);
    }

    public void chainMeeting(long id) {
        sendler.sendTextMessage(id, contactSearchChainMeeting);
    }

    public void takeAway(long id) {
        sendler.sendTextMessage(id, contactSearchTakeaway);
    }

    public void sickLeaveRequest(long id) {
        sendler.sendTextMessage(id, contactSearchSickLeaveRequest);
    }

    public void order(long id) {
        sendler.sendTextMessage(id, contactSearchOrder);
    }

    public void contract(long id) {
        sendler.sendTextMessage(id, contactSearchContract);
    }

    public void yesMenu(long id) {
        sendler.sendContactSearchYesMenu(id, contactSearchYesMessage);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH_YES);
    }

    public void stafferSearchMessage(long id) {
        sendler.sendBack(id, contactSearchStaffer);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH_STAFFER);
    }

    public void stafferSearchAction(long id, String text) {
        int maxResults = 25;
        Pageable pageable = PageRequest.of(0, maxResults);
        List<Office> offices = officeRepository.searchStaffer(text, pageable);
        StringBuilder resultBuilder = new StringBuilder();

        if (offices.isEmpty()) {
            resultBuilder.append(contactSearchStafferEmpty);
            String result = resultBuilder.toString();
            sendler.sendBack(id, result);
        } else {
            for (Office office : offices) {
                resultBuilder.append(office.getDepartment()).append("\n\n");
                resultBuilder.append(office.getStaffer()).append("\n");
                resultBuilder.append(office.getPhone()).append("\n");
                resultBuilder.append(office.getMail()).append("\n");
                resultBuilder.append("-----------------------------\n");
            }
            String result = resultBuilder.toString();
            sendler.sendContactSearchYesMenu(id, result);
            userStateService.setUserState(id, UserState.CONTACT_SEARCH_YES);
        }
    }

    public void gallerySearchMessage(long id) {
        sendler.sendBack(id, contactSearchGallery);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH_GALLERY);
    }

    public void gallerySearchAction(long id, String text) {
        int maxResults = 25;
        Pageable pageable = PageRequest.of(0, maxResults);
        List<Gallery> galleries = galleryRepository.searchGallery(text, pageable);
        StringBuilder resultBuilder = new StringBuilder();

        if (galleries.isEmpty()) {
            resultBuilder.append(contactSearchGalleryEmpty);
            String result = resultBuilder.toString();
            sendler.sendBack(id, result);
        } else {
            for (Gallery gallery : galleries) {
                resultBuilder.append(gallery.getGallery()).append("\n\n");
                resultBuilder.append(gallery.getCity()).append("\n");
                resultBuilder.append(gallery.getPhone()).append("\n");
                resultBuilder.append(gallery.getMail()).append("\n");
                resultBuilder.append("-----------------------------\n");
            }
            String result = resultBuilder.toString();
            sendler.sendContactSearchYesMenu(id, result);
            userStateService.setUserState(id, UserState.CONTACT_SEARCH_YES);
        }
    }


}
