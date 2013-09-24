#!/usr/bin/python

import time
import os

setting = {
    'dir': "svn://192.168.5.27/borqsservice/server/BorqsMarket3_branches" +
           time.strftime('%Y-%m-%d-%H-%M-%S', time.localtime()),
    'url': 'svn://192.168.5.27/borqsservice/server/BorqsMarket3',
    'user': 'b669',
    'pwd': 'account&Server6',
}


def branch():
    cmd = 'svn copy %(url)s %(dir)s --username %(user)s --password "%(pwd)s"' % setting
    print "execute %s" % cmd
    os.system(cmd)

branch()

