package com.hbx.reggie.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbx.reggie.dao.AddressBook;
import com.hbx.reggie.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
 * @author 黄柏轩
 * @version 1.0
 */
@Service
public class AddressBookServiceImp extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
