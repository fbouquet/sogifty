package com.sogifty.dao;

import com.sogifty.dao.dto.Preferences;

public class PreferencesDAO extends AbstractDAO<Preferences> {

	@Override
	public Class<Preferences> getType() {
		return Preferences.class;
	}
}
