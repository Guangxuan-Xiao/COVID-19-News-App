import igraph as ig
import json
import pickle
import cairo


def process_data():
    G = ig.Graph()
    id2idx = {}
    idx2id = []
    weight = []
    with open("event.json") as f:
        events_dict = json.load(f)
    for idx, event in enumerate(events_dict["data"]):
        id2idx[event["_id"]] = idx
        idx2id.append(event["_id"])
        G.add_vertices(idx)
    for idx, event in enumerate(events_dict["data"]):
        for edge in event["related_events"]:
            try:
                v = id2idx[edge["id"]]
            except KeyError:
                continue
            G.add_edges([(idx, v)])
            weight.append(edge["score"])
    G.es["weight"] = weight
    # with open("ig_graph.pkl", "wb") as f:
    #     pickle.dump(G, f)
    return G


if __name__ == "__main__":
    G = process_data()
    print("Data prepared!")
    G.write_svg("ig_plot.svg")
    ig.plot(G, target="ig_plot.png")
