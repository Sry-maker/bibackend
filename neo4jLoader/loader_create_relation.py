import logging
import os
from concurrent.futures import ThreadPoolExecutor

from py2neo import Graph, Relationship, NodeMatcher, errors

# 使用环境变量，防止密码泄露
graph = Graph(address=os.getenv("DATABASE_IP"),
              user=os.getenv("DATABASE_USER"),
              password=os.getenv("DATABASE_PWD"))
nodes = NodeMatcher(graph)

# logger 配置
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)


def create_coauthor_relation(line):

    lineData = line.split("\t")
    try:
        author1 = lineData[0][1:]
        author2 = lineData[1]
        times = int(lineData[2])
    except Exception:
        logger.exception("a_a_r data error index at: {}".format(lineData[0][1:]))

    try:
        node1 = nodes.match("AUTHOR", index=author1).first()
        node2 = nodes.match("AUTHOR", index=author2).first()
        r = Relationship(node1, "WRITE", node2, **{"count": times})
        graph.create(r)
    except errors.ClientError:
        logger.exception("create a_a_r error index at: {}".format(lineData[0][1:]))


def executor_callback(worker):
    worker_exception = worker.exception()
    if worker_exception:
        logger.exception("Worker return exception: {}".format(worker_exception))

if __name__ == '__main__':
    # WRITE = Relationship.type("WRITE")
    # nodes = NodeMatcher(graph)
    # graph.create(WRITE(nodes.match("AUTHOR").first(), nodes.match("PAPER").first()))

    # for item in list(nodes.match("1984")):
    #     print(item)

    # 建立作者合作关系
    with ThreadPoolExecutor(4) as t:
        file = open("data/AMiner-Coauthor/AMiner-Coauthor.txt", "r", encoding="UTF-8")
        line = file.readline()
        while line:
            future = t.submit(create_coauthor_relation, line)
            future.add_done_callback(executor_callback)
            line = file.readline()


