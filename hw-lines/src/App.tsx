/*
 * Copyright (C) 2022 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, { Component } from "react";
import EdgeList from "./EdgeList";
import Map from "./Map";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    edges: string[];
}

class App extends Component<{}, AppState> { // <- {} means no props.

  constructor(props: any) {
    super(props);
    this.state = {
      edges : []
    };
  }

  setEdgeList(edgesArray: string[]) {
      this.setState( {
          edges: edgesArray,
      })
  }

  tokenizeEdgeArray(edgeArray: string[]): any[] {
      let newEdgeArray: any[] = [];
      for (let i = 0; i < edgeArray.length; i++) {
          let edge: any[] = edgeArray[i].split(" ");
          if(edge.length !== 5) {
              alert("Edge input is not 5 tokens long.");
          } else if(isNaN(Number(edge[0]))) {
              alert("x1 of edge " + (i+1) + " is not a number.");
          } else if(isNaN(Number(edge[1]))) {
              alert("y1 of edge " + (i+1) + " is not a number.");
          } else if(isNaN(Number(edge[2]))) {
              alert("x2 of edge " + (i+1) + " is not a number.");
          } else if(isNaN(Number(edge[3]))) {
              alert("y2 of edge " + (i+1) + " is not a number.");
          } else {
              newEdgeArray[i] = [parseInt(edge[0]), parseInt(edge[1]), parseInt(edge[2]),
                  parseInt(edge[3]), edge[4]];
          }
      }
      return newEdgeArray;
  }

  render() {
    return (
      <div>
        <h1 id="app-title">Line Mapper!</h1>
        <div>
            <Map edges = {this.tokenizeEdgeArray(this.state.edges)}/>
        </div>
        <EdgeList
          onChange={(value) => {
            this.setEdgeList(value);
            console.log("EdgeList onChange", value);
          }}
        />
      </div>
    );
  }
}

export default App;
