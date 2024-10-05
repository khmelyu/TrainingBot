package trainingBot.service.action;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import trainingBot.dto.GalleryDto;
import trainingBot.dto.GalleryDtoBuilder;
import trainingBot.model.entity.Gallery;
import trainingBot.model.entity.Office;
import trainingBot.model.entity.Users;
import trainingBot.model.rep.GalleryRepository;
import trainingBot.model.rep.OfficeRepository;
import trainingBot.model.rep.UserRepository;
import trainingBot.service.redis.SearchDataService;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8")
public class ContactSearchAction {
    private final Sendler sendler;
    private final UserStateService userStateService;
    private final SearchDataService searchDataService;
    private final OfficeRepository officeRepository;
    private final GalleryRepository galleryRepository;
    private final UserRepository userRepository;

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
    @Value("${contact.search.city.choice}")
    private String contactSearchCityChoice;
    @Value("${contact.search.division.choice}")
    private String contactSearchDivisionChoice;

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

    public void otherDivisionSearchMessage(long id) {
        sendler.sendOtherDivisionMenu(id, contactSearchCityChoice);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH_OTHER_DIVISION);
    }
    public void cityChoiceMessage(long id, String text) {
        sendler.sendDivisionMenu(id, contactSearchDivisionChoice, text);
        searchDataService.setCity(id, text);
        userStateService.setUserState(id, UserState.CONTACT_SEARCH_DIVISION);

    }

    public void contactViewMessage(long id, String text) {
        List<Users> users = userRepository.findUsersByDivisionsAndCity(searchDataService.getCity(id), text);
        StringBuilder resultBuilder = new StringBuilder();
        for (Users user : users){
            if (!user.getName().equals("Куратор")) {
                resultBuilder.append(user.getName()).append("\n");
                resultBuilder.append(user.getLastname()).append("\n");
                resultBuilder.append(user.getPhone()).append("\n");
                resultBuilder.append("-----------------------------\n");
            }
        }
        sendler.sendTextMessage(id, resultBuilder.toString());

    }

    public void gallerySearchAction(long id, String text) {
        int maxResults = 6;
        Pageable pageable = PageRequest.of(0, maxResults);
        List<Gallery> galleries = galleryRepository.searchGallery(text, pageable);
        StringBuilder resultBuilder = new StringBuilder();

        if (galleries.isEmpty()) {
            resultBuilder.append(contactSearchGalleryEmpty);
            String result = resultBuilder.toString();
            sendler.sendBack(id, result);
        } else {
            for (Gallery gallery : galleries) {
                Users curator = userRepository.findCuratorByGallery(gallery.getId());
                Users manager = userRepository.findManagerByGallery(gallery.getId());
                GalleryDto galleryDto = GalleryDtoBuilder.createDto(gallery, curator, manager);

                resultBuilder.append(galleryDto.getGallery()).append("\n\n");
                resultBuilder.append(galleryDto.getGalleryCity()).append("\n");
                resultBuilder.append(galleryDto.getGalleryPhone()).append("\n");
                resultBuilder.append(galleryDto.getGalleryMail()).append("\n\n");
                if (!Objects.isNull(curator) && !curator.getName().equals("Куратор")) {
                    resultBuilder.append("Куратор:\n");
                    resultBuilder.append(galleryDto.getCuratorName()).append("\n");
                    resultBuilder.append(galleryDto.getCuratorLastname()).append("\n");
                    resultBuilder.append(galleryDto.getCuratorPhone()).append("\n\n");
                }
                if (!Objects.isNull(manager)) {
                    resultBuilder.append("Управляющий:\n");
                    resultBuilder.append(galleryDto.getManagerName()).append("\n");
                    resultBuilder.append(galleryDto.getManagerLastname()).append("\n");
                    resultBuilder.append(galleryDto.getManagerPhone()).append("\n");
                }
                resultBuilder.append("-----------------------------\n");

            }
            sendler.sendBack(id, resultBuilder.toString());
        }
    }
}
