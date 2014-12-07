package com.sogifty.dao;

import com.sogifty.dao.dto.Preferences;

public class PreferencesDAO extends AbstractDAO<Preferences> {
	public PreferencesDAO() {
		this.setType(Preferences.class);
	}
}
