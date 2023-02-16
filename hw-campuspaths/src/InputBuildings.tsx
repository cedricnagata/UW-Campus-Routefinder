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

import React, {Component} from 'react';

interface InputBuildingsProps {
    onChange: (start: string, end: string) => any;
}

interface InputBuildingsState {
    startInput: string;
    endInput: string;
    buildingList: any[];
}

/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */
class InputBuildings extends Component<InputBuildingsProps, InputBuildingsState> {
    constructor (props: any) {
        super(props);
        this.state = {startInput: "", endInput: "", buildingList: []};
    }

    handleChangeStart = (event: any) => {
        this.setState({
            startInput: event.target.value,
        })
    }

    handleChangeEnd = (event: any) => {
        this.setState({
            endInput: event.target.value,
        })
    }

    setStart(start: string) {
        this.setState({
            startInput: start
        })
    }

    setEnd(end: string) {
        this.setState({
            endInput: end
        })
    }

    parseBuildings = async () => {
        try {
            let response = await fetch("http://localhost:4567/building-names");

            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return;
            }

            let buildingPromise = response.json();
            let buildingText = await buildingPromise;

            this.setState( {
                buildingList: buildingText,
            })
        } catch (e) {
            console.log(e);
        }
    }

    createDropdownList() : JSX.Element[]{
        let dropdownList: JSX.Element[] = [];

        for(let i in this.state.buildingList) {
            dropdownList.push(<option value ={i}>{this.state.buildingList[i]}</option>);
        }

        return dropdownList;
    }

    render() {
        let dropdownList: JSX.Element[] = this.createDropdownList();
        void this.parseBuildings()
        return (
            <div
                style = {{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center"
                }} id="building-inputs">
                <div>
                    <select style = {{margin: 5}} id = "start" value={this.state.startInput} onChange={this.handleChangeStart}>
                        <option id = "start" value = "starting building">{"Select a starting building"}</option>
                        {dropdownList}
                    </select>
                    <select style = {{margin: 5}} id = "end" value={this.state.endInput} onChange={this.handleChangeEnd}>
                        <option id = "end" value = "ending building">{"Select an ending building"}</option>
                        {dropdownList}
                    </select>

                    <div style = {{
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                    }} id="findPath-button">
                        <button onClick={() => {
                            this.props.onChange(this.state.startInput, this.state.endInput);
                        }}>Find Path</button>
                        <button style = {{margin: 5}} onClick={() => {
                            let emptyStart: string = ""
                            let emptyEnd: string = ""

                            this.props.onChange(emptyStart, emptyEnd);

                            this.setStart(emptyStart);
                            this.setEnd(emptyEnd);
                        }}>Reset</button>
                    </div>
                </div>
            </div>
        );
    }
}




export default InputBuildings;
