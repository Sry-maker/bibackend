import logging
import os

from py2neo import Graph, Relationship, NodeMatcher, Node, errors

# 使用环境变量，防止密码泄露
graph = Graph(address=os.getenv("DATABASE_IP"),
              user=os.getenv("DATABASE_USER"),
              password=os.getenv("DATABASE_PWD"))
nodes = NodeMatcher(graph)

# logger 配置
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)


def executor_callback(worker):
    worker_exception = worker.exception()
    if worker_exception:
        logger.exception("Worker return exception: {}".format(worker_exception))


def create_coauthor_relation():
    file = open("data/AMiner-Coauthor/AMiner-Coauthor.txt", "r", encoding="UTF-8")
    line = file.readline()
    while line:
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
            r = Relationship(node1, "co_author", node2, **{"count": times})
            graph.create(r)
        except Exception:
            logger.exception("create a_a_r error index at: {}".format(lineData[0][1:]))
        line = file.readline()


def create_write_relation():
    file = open("data/AMiner-Paper/AMiner-Paper.txt", "r", encoding="UTF-8")
    line = file.readline()
    cur_paper_node = None
    while line:
        line6 = line[:6]
        line2 = line[:2]
        line_end = line[3:-1]

        if line6 == "#index":
            cur_paper_node = nodes.match("PAPER", index=line[7:-1]).first()
        elif line2 == "#@":
            for author in line_end.split(";"):
                # 无效字段跳过
                if author == "-" or len(author) == 0:
                    continue
                try:
                    author_node = nodes.match("AUTHOR", name=author).first()
                    r = Relationship(author_node, "write", cur_paper_node)
                    graph.create(r)
                except Exception:
                    logger.exception("create write error at author of name: {}".format(author))
        else:
            pass
        line = file.readline()


def create_refer_relation():
    file = open("data/AMiner-Paper/AMiner-Paper.txt", "r", encoding="UTF-8")
    line = file.readline()
    while line:
        line6 = line[:6]
        if line6 == "#index":
            cur_paper_node = nodes.match("PAPER", index=line[7:-1]).first()
            # 没有对应属性的话跳过
            if cur_paper_node["ref"] is None:
                line = file.readline()
                continue
            for ref in cur_paper_node["ref"].split(";"):
                try:
                    ref_paper_node = nodes.match("PAPER", index=ref).first()
                    r = Relationship(cur_paper_node, "refer", ref_paper_node)
                    graph.create(r)
                except Exception:
                    logger.exception("create refer error at paper of index: {}".format(line[7:-1]))
        else:
            pass
        line = file.readline()


def create_publish_relation():
    file = open("data/AMiner-Paper/AMiner-Paper.txt", "r", encoding="UTF-8")
    line = file.readline()
    cur_paper_node = None
    while line:
        line6 = line[:6]
        line2 = line[:2]
        line_end = line[3:-1]

        if line6 == "#index":
            cur_paper_node = nodes.match("PAPER", index=line[7:-1]).first()
        elif line2 == "#c":
            try:
                venue_node = nodes.match("VENUE", name=line_end).first()
                r = Relationship(venue_node, "publish", cur_paper_node)
                graph.create(r)
            except Exception:
                logger.exception("create publish error at paper of index: {}".format(line[7:-1]))
        else:
            pass
        line = file.readline()


def create_belong_to_relation():
    file = open("data/AMiner-Author/AMiner-Author.txt", "r", encoding="UTF-8")
    line = file.readline()
    while line:
        line6 = line[:6]
        if line6 == "#index":
            cur_author_node = nodes.match("AUTHOR", index=line[7:-1]).first()
            # 没有对应属性的话跳过
            if cur_author_node["affiliations"] is None:
                line = file.readline()
                continue
            for affiliation in cur_author_node["affiliations"].split(";"):
                if affiliation == "-" or len(affiliation) == 0:
                    continue
                try:
                    affiliation_node = nodes.match("AFFILIATION", name=affiliation).first()
                    r = Relationship(cur_author_node, "belong_to", affiliation_node)
                    graph.create(r)
                except Exception:
                    logger.exception("create belong_to error at author of index: {}".format(line[7:-1]))
        else:
            pass
        line = file.readline()


def create_has_interest_relation():
    file = open("data/AMiner-Author/AMiner-Author.txt", "r", encoding="UTF-8")
    line = file.readline()
    while line:
        line6 = line[:6]
        if line6 == "#index":
            cur_author_node = nodes.match("AUTHOR", index=line[7:-1]).first()
            if cur_author_node["interests"] is None:
                line = file.readline()
                continue
            for interest in cur_author_node["interests"].split(";"):
                try:
                    interest_node = nodes.match("INTEREST", name=interest).first()
                    # 仍然有节点没有储存上，原因复杂
                    if interest_node is None and interest != "" and interest != "-":
                        print("create interest: ", interest)
                        interest_node = Node("INTEREST")
                        interest_node["name"] = interest
                        try:
                            graph.create(interest_node)
                        except errors.ClientError:
                            logger.exception()
                    r = Relationship(cur_author_node, "has_interest", interest_node)
                    graph.create(r)
                except Exception:
                    logger.exception("create has_interest error at author of index: {}".format(line[7:-1]))
        else:
            pass
        line = file.readline()


if __name__ == '__main__':
    # WRITE = Relationship.type("WRITE")
    # nodes = NodeMatcher(graph)
    # graph.create(WRITE(nodes.match("AUTHOR").first(), nodes.match("PAPER").first()))

    # for item in list(nodes.match("1984")):
    #     print(item)

    print("start")
    # 建立co_author关系
    # create_coauthor_relation() √
    print("create_coauthor_relation()")

    # 建立write关系
    # create_write_relation()  √
    print("create_write_relation()")

    # 建立refer关系
    # create_refer_relation() √
    print("create_refer_relation()")

    # 建立publish关系
    # create_publish_relation() √
    print("create_publish_relation()")

    # 建立belong_to关系
    # create_belong_to_relation()
    print("create_belong_to_relation()")

    # 建立has_interest关系
    # create_has_interest_relation()
    print("create_has_interest_relation()")
