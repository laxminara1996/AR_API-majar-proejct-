package in.ar.service;

import java.util.List;

import in.ar.binding.App;
import in.ar.entity.AppEntity;

public interface ARService {
public String createApplication(App c);
public List<App> fetchApps(Long userId);
}
