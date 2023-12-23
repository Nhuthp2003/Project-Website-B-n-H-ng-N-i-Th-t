package com.arphor.service.impi;

import org.springframework.stereotype.Service;

import com.arphor.dao.AuthorityDAO;
import com.arphor.entity.Authority;
import com.arphor.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	private final AuthorityDAO authorityDAO;

    public AuthorityServiceImpl(AuthorityDAO authorityDAO) {
        this.authorityDAO = authorityDAO;
    }

    @Override
    public void saveAuthority(Authority authority) {
    	authorityDAO.save(authority);
    }
}
