package com.skcc.egovcore.core.mvc.service;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public abstract class SkAbstractService extends EgovAbstractServiceImpl {
}
