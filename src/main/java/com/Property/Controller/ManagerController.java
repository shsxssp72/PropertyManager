package com.Property.Controller;

import com.Property.Service.ManagerService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiresRoles("manager")
@RequestMapping("/manager")
public class ManagerController
{

	@Autowired
	private ManagerService managerService;

    /*@RequestMapping(value = "/proprietor", method = RequestMethod.PUT)
    public*/
}
