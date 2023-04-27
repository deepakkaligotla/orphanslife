import React from 'react';
import { useState } from "react";
import '../Asset/Css/Body.css';


function Locations()
{
    const loginAPI = 'http://localhost:4000/admins';
    const [locations, setlocations] =  useState([]);
    const [message, setmessage] = useState("");
    const [pin, setpin] = useState({pincode:"411057"});

    var copypincode = {...pin};

    const handleChange = (args)=>{
        copypincode[args.target.name] = args.target.value;
        console.log(copypincode)
        setpin(copypincode)
        }

    function myFunction() {
        var helper = new XMLHttpRequest();
        var filter, li, a, i, txtValue;
        for (i = 0; i < locations.length; i++) {
            a = li[i].getElementsByTagName("a")[0];
            txtValue = a.textContent || a.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                li[i].style.display = "";
            } else {
                li[i].style.display = "none";
            }
        }
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                    if(result.data.length > 0)
                    {
                       setmessage("Pincode found!");
                       for (i = 0; i < result.data.length; i++)
                            setlocations[i](result.data[i]);
                    }
                    else
                    {
                       setmessage("Pincode NOT FOUND.!")
                    }
            }
        };
        const url = "http://localhost:4000/alllocations/"
        console.log(url)
        helper.open("GET",url);
        helper.setRequestHeader("Content-Type", "application/json");
        helper.setRequestHeader("Access-Control-Allow-Origin", "*");
        helper.setRequestHeader("Access-Control-Allow-Methods", "*");
        helper.setRequestHeader("Access-Control-Allow-Headers", "*");
        helper.setRequestHeader({ headers: {"x-auth-token" : `${localStorage.getItem('user-token')}`}});
        helper.send();
    }

    return (<React.Fragment>
        <><div id="search">
            <input type="text" id="pincode" onkeyup={myFunction()} placeholder="Search for pincodes.." onChange={handleChange} title="Pincode live filter Search"/><br></br><br></br>
            <h6 style={{color: "orange"}}>{message}</h6>
            </div><br></br>
            {locations.map(location=> {
                return <ul id="myUL">
                            <li><a href="#">{location.pincode}</a></li>
                        </ul>})}
            </>
    </React.Fragment>
    );
}

export default Locations;