import os
from py2neo import Graph, Node, Relationship, NodeMatcher

# 使用环境变量，防止密码泄露
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
            # 会议标签
            if line_end != "" and line_end != "-":
                paper_node.add_label(line_end)
        elif line2 == "#%":
            # 特殊处理
            if paper_node["ref"] is not None:
                line_end = paper_node["ref"] + ";" + line_end
            paper_node["ref"] = line_end
        elif line2 == "#!":
            paper_node["abstract"] = line_end
        else:
            count += 1;
            print("create:", count)
            # 创建节点并重置paper_node
            graph.create(paper_node)
            paper_node = Node("PAPER")

        if count == 100:
            break

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
            # 添加作者的隶属单位
            for label in line_end2.split(";"):
                if label != "" and label != "-":
                    author_node.add_label(label)
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
            author_node["interests"] = line_end3
            # 添加作者的兴趣领域
            for label in line_end3.split(";"):
                if label != "" and label != "-":
                    author_node.add_label(label)
        else:
            count += 1
            print(line)
            print("create: ", count)
            graph.create(author_node)
            author_node = Node("AUTHOR")

        if count == 100:
            break
        line = file.readline()


if __name__ == '__main__':
    # read_paper()
    # read_author()

    WRITE = Relationship.type("WRITE")
    nodes = NodeMatcher(graph)
    graph.create(WRITE(nodes.match("AUTHOR").first(), nodes.match("PAPER").first()))

    for item in list(nodes.match("1984")):
        print(item)
