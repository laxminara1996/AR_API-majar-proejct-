package in.ar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import in.ar.binding.App;
import in.ar.constants.AppConstants;
import in.ar.entity.AppEntity;
import in.ar.entity.UserEntity;
import in.ar.exception.SsaWebException;
import in.ar.repo.AppRepo;
import in.ar.repo.UserRepo;

@Service
public class ARServiceImpl implements ARService {

	@Autowired
	private AppRepo appRepo;

	@Autowired
	private UserRepo userRepo;

	private static final String SSA_WEB_API_URL = "https://ssa.web.app/{ssn}";

	public String createApplication(App c) {
		try {
			WebClient webClient = WebClient.create();

			String stateName = webClient.get().uri(SSA_WEB_API_URL, c.getSsn()).retrieve().bodyToMono(String.class)
					.block();

			if (AppConstants.RI.equals(stateName)) {

				UserEntity userEntity = userRepo.findById(c.getUserId()).get();

				AppEntity appEntity = new AppEntity();
				BeanUtils.copyProperties(c, appEntity);

				appEntity.setUser(userEntity);

				appEntity = appRepo.save(appEntity);
				return "App Created with Case Num : " + appEntity.getCaseNum();
			}
		} catch (Exception e) {
			throw new SsaWebException(e.getMessage());
		}

		return AppConstants.INVALID_SSN;
	}

	@Override
	public List<App> fetchApps(Long userId) {

		UserEntity userEntity = userRepo.findById(userId).get();
		Integer roleId = userEntity.getRoleId();

		List<AppEntity> appEntities = null;

		if (1 == roleId) {
			appEntities = appRepo.fetchUserApps();
		} else 
			appEntities = appRepo.fetchCwApps(userId);
		
		
		List<App> apps = new ArrayList<>();
		
		for(AppEntity entity : appEntities) {
			App app = new App();
			BeanUtils.copyProperties(entity, apps);
			apps.add(app);
		}

		return apps;
	}

}