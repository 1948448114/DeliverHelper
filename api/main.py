# -*- coding: utf-8 -*-
#@date  :2015-3-from

from sqlalchemy.orm import scoped_session, sessionmaker
from mod.database.db import engine
import tornado.web
import tornado.ioloop
import tornado.httpserver
import tornado.options
import tornado.gen
import os
import random

from mod.getRecord import RecordListHandler
from mod.newRecord import NewRecordHandler

from tornado.options import define, options
define('port', default=8000, help='run on the given port', type=int)

class Application(tornado.web.Application):

    def __init__(self):
        handlers = [
             (r'/api/list',RecordListHandler),
             (r'/api/new',NewRecordHandler)
            ]
        settings = dict(
            cookie_secret="7CA71A57B571B5AEAC5E64C6042415DE",
            template_path=os.path.join(os.path.dirname(__file__), 'templates'),
            static_path=os.path.join(os.path.dirname(__file__), 'static'),
            debug=True
        )
        tornado.web.Application.__init__(self, handlers, **settings)
        self.db = scoped_session(sessionmaker(bind=engine,
                                              autocommit=False, autoflush=True,
                                              expire_on_commit=False))



if __name__ == '__main__':
    tornado.options.parse_command_line()
    Application().listen(options.port)
    tornado.ioloop.IOLoop.instance().start()