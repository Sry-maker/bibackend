import os
from py2neo import Graph, Node, Relationship, NodeMatcher, errors

# 使用环境变量获取连接信息
graph = Graph(address=os.getenv("DATABASE_IP"),
              user=os.getenv("DATABASE_USER"),
              password=os.getenv("DATABASE_PWD"))


# 创建paper节点
def read_paper():
    file = open("data/AMiner-Paper/AMiner-Paper.txt", "r", encoding="UTF-8")
    count = 0
    # 节点标签为Paper
    paper_node = Node("PAPER")
    line = file.readline()
    while line:
        line6 = line[:6]
        line2 = line[:2]
        line_end = line[3:-1]
        if line6 == "#index":
            paper_node["index"] = line[7:-1]
        elif line2 == "#*":
            paper_node["title"] = line_end
        elif line2 == "#@":
            paper_node["authors"] = line_end
        elif line2 == "#o":
            paper_node["affiliations"] = line_end
        elif line2 == "#t":
            paper_node["year"] = line_end
            # 年标签
            if line_end != "" and line_end != "-":
                paper_node.add_label(line_end)
        elif line2 == "#c":
            paper_node["venue"] = line_end
            # 创建会议
            if line_end != "" and line_end != "-":
                create_venue(line_end)
        elif line2 == "#%":
            # 特殊处理
            if paper_node["ref"] is not None:
                line_end = paper_node["ref"] + ";" + line_end
            paper_node["ref"] = line_end
        elif line2 == "#!":
            paper_node["abstract"] = line_end
        else:
            # count += 1;
            # print("create:", count)
            # 创建节点并重置paper_node
            try:
                graph.create(paper_node)
            except errors.ClientError:
                print("error create PAPER at index: " + paper_node["index"])
            paper_node = Node("PAPER")

        # if count == 100:
        #     break

        line = file.readline()


# 创建author节点
def read_author():
    file = open("data/AMiner-Author/AMiner-Author.txt", "r", encoding="UTF-8")
    count = 0
    author_node = Node("AUTHOR")
    line = file.readline()
    while line:
        line6 = line[:6]
        line2 = line[:2]
        line3 = line[:3]
        line_end2 = line[3:-1]
        line_end3 = line[4:-1]
        if line6 == "#index":
            author_node["index"] = line[7:-1]
        elif line2 == "#n":
            author_node["name"] = line_end2
        elif line2 == "#a":
            author_node["affiliations"] = line_end2
            # 创建作者的隶属单位
            for label in line_end2.split(";"):
                if label != "" and label != "-":
                    create_affiliation(label)
        elif line3 == "#pc":
            author_node["publishCount"] = line_end3
        elif line3 == "#cn":
            author_node["referenceCount"] = line_end3
        elif line3 == "#hi":
            author_node["Hindex"] = line_end3
        elif line3 == "#pi":
            author_node["Pindex"] = line_end3
        elif line[:4] == "#upi":
            author_node["UPindex"] = line[5:-1]
        elif line2 == "#t":
            author_node["interests"] = line_end2
            # 创建作者的兴趣领域
            for label in line_end3.split(";"):
                if label != "" and label != "-":
                    create_interest(label)
        else:
            # count += 1
            # print("create: ", count)
            try:
                graph.create(author_node)
            except errors.ClientError:
                print("error create AUTHOR at index: " + author_node["index"])
            author_node = Node("AUTHOR")

        # if count == 100:
        #     break
        line = file.readline()


def create_venue(venue_name):
    venue_node = Node("VENUE")
    venue_node["name"] = venue_name
    # 会出现重复的值，处理这个error
    try:
        graph.create(venue_node)
    except errors.ClientError:
        pass


def create_affiliation(affiliation_name):
    affiliation_node = Node("AFFILIATION")
    affiliation_node["name"] = affiliation_name
    try:
        graph.create(affiliation_node)
    except errors.ClientError:
        pass


def create_interest(interest_name):
    interest_node = Node("INTEREST")
    interest_node["name"] = interest_name
    try:
        graph.create(interest_node)
    except errors.ClientError:
        pass


if __name__ == '__main__':
    # unique约束
    constraint1 = "CREATE CONSTRAINT ON (venue:VENUE) " \
                  "ASSERT venue.name IS UNIQUE"
    try:
        graph.run(constraint1)
    except errors.ClientError:
        print("constraint1 已存在")
    constraint2 = "CREATE CONSTRAINT ON (affiliation:AFFILIATION) " \
                  "ASSERT affiliation.name IS UNIQUE"
    try:
        graph.run(constraint2)
    except errors.ClientError:
        print("constraint2 已存在")
    constraint3 = "CREATE CONSTRAINT ON (interest:INTEREST) " \
                  "ASSERT interest.name IS UNIQUE"
    try:
        graph.run(constraint3)
    except errors.ClientError:
        print("constraint3 已存在")

    #  节点创建
    ## read_paper()
    ## read_author()

    # 将节点的数值属性修改正确

    # 创建索引
    index1 = "CREATE INDEX ON :AUTHOR (index)"
    graph.run(index1)
    index2 = "CREATE INDEX ON :PAPER (index)"
    graph.run(index2)
    index3 = "CREATE INDEX ON :AUTHOR (name)"
    graph.run(index3)
    # 有unique的属性不需要创建索引，已经存在默认的索引





    # WRITE = Relationship.type("WRITE")
    # nodes = NodeMatcher(graph)
    # graph.create(WRITE(nodes.match("AUTHOR").first(), nodes.match("PAPER").first()))
    #
    # for item in list(nodes.match("1984")):
    #     print(item)
