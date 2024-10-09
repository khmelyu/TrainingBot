package trainingBot.service.action;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import trainingBot.model.entity.Ambassador2024;
import trainingBot.model.entity.Users;
import trainingBot.model.rep.Ambassador2024Repository;
import trainingBot.service.redis.UserState;
import trainingBot.service.redis.UserStateService;
import trainingBot.view.Sendler;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PropertySources({@PropertySource(value = "classpath:messages.txt", encoding = "UTF-8"), @PropertySource(value = "classpath:pictures.txt", encoding = "UTF-8")})
public class AmbassadorAction {

    private final Sendler sendler;
    private final UserStateService userStateService;
    private final Ambassador2024Repository ambassador2024Repository;

    @Value("${ambassador.pic}")
    private String ambassadorPic;
    @Value("${ambassador.yes.message}")
    private String ambassadorYesMessage;
    @Value("${ambassador.excellent.message}")
    private String ambassadorExcellentMessage;
    @Value("${ambassador.ready.message}")
    private String ambassadorReadyMessage;
    @Value("${ambassador.create.team.message}")
    private String ambassadorCreateTeamMessage;
    @Value("${ambassador.final.message}")
    private String ambassadorFinalMessage;
    @Value("${ambassador.final.message.join}")
    private String ambassadorFinalMessageJoin;
    @Value("${ambassador.empty}")
    private String ambassadorEmpty;
    @Value("${ambassador.task.list}")
    private String ambassadorTaskList;
    @Value("${ambassador.test.pic}")
    private String ambassadorTestPic;
    @Value("${ambassador.test}")
    private String ambassadorTest;
    @Value("${ambassador.test.answer}")
    private String ambassadorTestAnswer;
    @Value("${ambassador.one.word}")
    private String ambassadorOneWord;
    @Value("${ambassador.one.word.answer}")
    private String ambassadorOneWordAnswer;
    @Value("${ambassador.voice}")
    private String ambassadorVoice;
    @Value("${ambassador.accepted}")
    private String ambassadorAccepted;
    @Value("${ambassador.letter}")
    private String ambassadorLetter;
    @Value("${ambassador.again.answer}")
    private String ambassadorAgainAnswer;


    public void ambassadorYes(long id, Message currentMessage) {
        sendler.sendAmbassadorYesMenu(id, ambassadorPic, ambassadorYesMessage, currentMessage);
    }

    public void ambassadorExcellent(long id, Message currentMessage) {
        sendler.sendAmbassadorExcellentMenu(id, ambassadorPic, ambassadorExcellentMessage, currentMessage);
    }

    public void ambassadorReady(long id, Message currentMessage) {
        sendler.sendAmbassadorReadyMenu(id, ambassadorPic, ambassadorReadyMessage, currentMessage);
    }

    public void createTeam(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        userStateService.setUserState(id, UserState.AMBASSADOR_CREATE_TEAM);
        sendler.callbackAnswer(update);
        sendler.sendAbort(id, ambassadorCreateTeamMessage);
    }

    public void saveNewTeam(long id, String text) {
        Ambassador2024 ambassador = ambassador2024Repository.findById(id).orElse(new Ambassador2024());
        ambassador.setId(id);
        ambassador.setTeam(text);
        ambassador2024Repository.save(ambassador);
        sendler.sendMainMenu(id, ambassadorFinalMessage);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }


    public void joinTeam(long id, Message currentMessage, int page) {
        List<Ambassador2024> ambassadors = ambassador2024Repository.findAll();
        if (ambassadors.isEmpty()) {
            sendler.sendTextMessage(id, ambassadorEmpty);
        } else {
            sendler.sendAmbassadorTeamListMenu(id, ambassadorPic, currentMessage, page);
            userStateService.setUserState(id, UserState.AMBASSADOR_JOIN_TEAM);
        }
    }

    public void saveJoinTeam(Update update) {
        long id = update.getCallbackQuery().getMessage().getChatId();
        String text = update.getCallbackQuery().getData();
        Ambassador2024 ambassador = ambassador2024Repository.findById(id).orElse(new Ambassador2024());
        ambassador.setId(id);
        ambassador.setTeam(text);
        ambassador2024Repository.save(ambassador);
        sendler.sendMainMenu(id, ambassadorFinalMessageJoin);
        sendler.callbackAnswer(update);
        userStateService.setUserState(id, UserState.MAIN_MENU);
    }

    public void viewMyTeam(Long id) {
        StringBuilder teamMembers = new StringBuilder();
        Optional<Ambassador2024> optionalAmbassador2024 = ambassador2024Repository.findById(id);
        if (optionalAmbassador2024.isPresent()) {
            int points = 0;
            Ambassador2024 ambassador2024 = optionalAmbassador2024.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(ambassador2024.getTeam());

            teamMembers.append("Команда: ").append(ambassador2024.getTeam()).append("\n\n");
            for (Users user : users) {
                teamMembers.append(user.getName()).append(" ").append(user.getLastname()).append("\n");
                Optional<Ambassador2024> optionalAmbassador = ambassador2024Repository.findById(user.getId());
                points += optionalAmbassador.get().getPoints();
            }
            teamMembers.append("\nВсего очков: ").append(points);
        }
        sendler.sendTextMessage(id, teamMembers.toString());
    }

    public void ambassadorTasks(Long id) {
        sendler.ambassadorTasksMenu(id, ambassadorTaskList);
        userStateService.setUserState(id, UserState.AMBASSADOR_TASKS);
    }

    public void ambassadorTest(Long id) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        if (ambassador.isPresent()) {
            Ambassador2024 ambassador2024 = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(ambassador2024.getTeam());
            boolean test = false;
            for (Users user : users) {
                Optional<String> teamAnswerTest = Optional.ofNullable(ambassador2024Repository.teamAnswerTest(user.getId()));
                if (teamAnswerTest.isPresent()) {
                    test = true;
                }
            }
            if (!test) {
                sendler.ambassadorTestMenu(id, ambassadorTest, ambassadorTestPic);
                userStateService.setUserState(id, UserState.AMBASSADOR_TEST);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }

    public void ambassadorTestAction(Long id, String text) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        StringBuilder answer = new StringBuilder();

        if (ambassador.isPresent()) {
            Ambassador2024 currentAmbassador = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(currentAmbassador.getTeam());
            boolean test = false;
            for (Users user : users) {
                Optional<String> teamAnswerTest = Optional.ofNullable(ambassador2024Repository.teamAnswerTest(user.getId()));
                if (teamAnswerTest.isPresent()) {
                    test = true;
                }
            }
            if (!test) {
                currentAmbassador.setTest_1_answer(text);
                if (text.equals("1")) {
                    answer.append("✅").append("\n").append(ambassadorTestAnswer);
                    currentAmbassador.setPoints(currentAmbassador.getPoints() + 100);
                } else {
                    answer.append("❌").append("\n").append(ambassadorTestAnswer);
                }
                ambassador2024Repository.save(currentAmbassador);
                sendler.sendMainMenu(id, answer.toString());
                userStateService.setUserState(id, UserState.MAIN_MENU);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }

    public void ambassadorOneWord(Long id) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        if (ambassador.isPresent()) {
            Ambassador2024 ambassador2024 = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(ambassador2024.getTeam());
            boolean word = false;
            for (Users user : users) {
                Optional<String> teamAnswerWord = Optional.ofNullable(ambassador2024Repository.teamAnswerOneWord(user.getId()));
                if (teamAnswerWord.isPresent()) {
                    word = true;
                }
            }
            if (!word) {
                sendler.sendBack(id, ambassadorOneWord);
                userStateService.setUserState(id, UserState.AMBASSADOR_ONE_WORD);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }

    public void ambassadorOneWordAction(Long id, String text) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        StringBuilder answer = new StringBuilder();
        if (ambassador.isPresent()) {
            Ambassador2024 currentAmbassador = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(currentAmbassador.getTeam());
            boolean word = false;
            for (Users user : users) {
                Optional<String> teamAnswerWord = Optional.ofNullable(ambassador2024Repository.teamAnswerOneWord(user.getId()));
                if (teamAnswerWord.isPresent()) {
                    word = true;
                }
            }
            if (!word) {
                currentAmbassador.setWord_1_answer(text);
                if (text.equalsIgnoreCase("вопрос") || text.equalsIgnoreCase("уточнение")) {
                    answer.append("✅").append("\n").append(ambassadorOneWordAnswer);
                    currentAmbassador.setPoints(currentAmbassador.getPoints() + 100);
                } else {
                    answer.append("❌").append("\n").append(ambassadorOneWordAnswer);
                }
                ambassador2024Repository.save(currentAmbassador);
                sendler.sendMainMenu(id, answer.toString());
                userStateService.setUserState(id, UserState.MAIN_MENU);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }

    public void ambassadorMedia(Long id) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        if (ambassador.isPresent()) {
            Ambassador2024 ambassador2024 = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(ambassador2024.getTeam());
            boolean media = false;
            for (Users user : users) {
                Optional<String> teamAnswerMedia = Optional.ofNullable(ambassador2024Repository.teamAnswerMedia(user.getId()));
                if (teamAnswerMedia.isPresent()) {
                    media = true;
                }
            }
            if (!media) {
                sendler.sendBack(id, ambassadorVoice);
                userStateService.setUserState(id, UserState.AMBASSADOR_MEDIA);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }

    public void ambassadorMediaAction(Long id, String voice) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        if (ambassador.isPresent()) {
            Ambassador2024 currentAmbassador = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(currentAmbassador.getTeam());
            boolean media = false;
            for (Users user : users) {
                Optional<String> teamAnswerMedia = Optional.ofNullable(ambassador2024Repository.teamAnswerMedia(user.getId()));
                if (teamAnswerMedia.isPresent()) {
                    media = true;
                }
            }
            if (!media) {
                currentAmbassador.setMedia_1_answer(voice);
                ambassador2024Repository.save(currentAmbassador);
                sendler.sendVoiceMessage(353185452L, currentAmbassador.getTeam(), voice);
                sendler.sendMainMenu(id, ambassadorAccepted);
                userStateService.setUserState(id, UserState.MAIN_MENU);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }

    public void ambassadorLetter(Long id) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        if (ambassador.isPresent()) {
            Ambassador2024 ambassador2024 = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(ambassador2024.getTeam());
            boolean letter = false;
            for (Users user : users) {
                Optional<String> teamAnswerLetter = Optional.ofNullable(ambassador2024Repository.teamAnswerLetter(user.getId()));
                if (teamAnswerLetter.isPresent()) {
                    letter = true;
                }
            }
            if (!letter) {
                sendler.sendBack(id, ambassadorLetter);
                userStateService.setUserState(id, UserState.AMBASSADOR_LETTER);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }

    public void ambassadorLetterAction(Long id, String text) {
        Optional<Ambassador2024> ambassador = ambassador2024Repository.findById(id);
        if (ambassador.isPresent()) {
            Ambassador2024 currentAmbassador = ambassador.get();
            List<Users> users = ambassador2024Repository.findMembersByTeam(currentAmbassador.getTeam());
            boolean letter = false;
            for (Users user : users) {
                Optional<String> teamAnswerLetter = Optional.ofNullable(ambassador2024Repository.teamAnswerLetter(user.getId()));
                if (teamAnswerLetter.isPresent()) {
                    letter = true;
                }
            }
            if (!letter) {
                currentAmbassador.setLetter_1_answer(text);
                ambassador2024Repository.save(currentAmbassador);
                sendler.sendMainMenu(id, ambassadorAccepted);
                userStateService.setUserState(id, UserState.MAIN_MENU);
            } else {
                sendler.sendTextMessage(id, ambassadorAgainAnswer);
            }
        }
    }
}
