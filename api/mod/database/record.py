#!/usr/bin/env python
# -*- coding: utf-8 -*-

from sqlalchemy import Column, String, Integer, ForeignKey
from sqlalchemy.ext.declarative import declarative_base
from db import engine, Base

class Record(Base):
    __tablename__ = 'record'
    id = Column(Integer, primary_key=True)
    name = Column(String(24))
    phone = Column(String(24))
    detail = Column(String(128))
    location = Column(String(128))
    state = Column(Integer)


