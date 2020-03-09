package gamehub.gamebind.cronjob;

import gamehub.gamebind.repository.GameBindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemoveOldBindsJob {

    private GameBindRepository gameBindRepository;

    @Scheduled(cron = "* */5 * * * *")
    public void perform() {
        gameBindRepository.findGamesToRemove().forEach(gameBindRepository::remove);
    }

    @Autowired
    public void setGameBindRepository(GameBindRepository gameBindRepository) {
        this.gameBindRepository = gameBindRepository;
    }
}
