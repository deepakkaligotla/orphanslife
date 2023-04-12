import React from 'react';

import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';


function AddSponsor()
{
    const [emp, setEmp] = useState({No: 0, Name: "", Address: ""});
    const [message, setmessage] = useState("");
    const [shouldCleanTextBoxes, setShouldCleanTextBoxes] = useState(false);

    const handleChange = (args)=>
    {
        var copyOfCurrentEmpInState = {...emp};
        copyOfCurrentEmpInState[args.target.name] = args.target.value;
        setEmp(copyOfCurrentEmpInState);
    }

    useEffect(()=>{
        if(message!=="")
        {
            setTimeout(() => 
            {
                setmessage("");
            }, 2000);
        }
    }, [message]);

    useEffect(()=>
    {
        if(shouldCleanTextBoxes === true)
        {
            setEmp({No: 0, Name: "", Address: ""});
        }
    }, [shouldCleanTextBoxes])

    const addRecord =()=>
    {
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 4 && helper.status === 200)
            {
                debugger;
                var result = JSON.parse(helper.responseText);
                if(result.affectedRows!==undefined)
                {
                    if(result.affectedRows > 0)
                    {
                       setmessage("Record Added Successfully!");
                       setShouldCleanTextBoxes(true);
                    }
                    else
                    {
                       setmessage("We could not add the record.!")
                       setShouldCleanTextBoxes(false);
                    }
                }
                else
                {
                    setmessage("Something went wrong! Try Again!"); 
                    setShouldCleanTextBoxes(false);  
                }
            }
        };
        helper.open("POST","http://http://localhost:4000/newsponsor");
        helper.setRequestHeader("Content-Type", "application/json")
        debugger;
        helper.send(JSON.stringify(emp));
    }

    const clearRecord =()=>
    {
        setEmp({});
    }
    
    return <div>
               <center>
                    <br></br>
                    <br></br>
                    <br></br>
                    <table>
                        <tbody>
                            <tr>
                                <td className='td'>No</td>    
                                <td className='td'>
                                    <input type="text" name="No"
                                           value={emp.No}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Name</td>    
                                <td className='td'>
                                    <input type="text" name="Name"
                                           value={emp.Name}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Address</td>    
                                <td className='td'>
                                    <input type="text" name="Address"
                                           value={emp.Address}
                                           onChange={handleChange}/>
                                </td>
                            </tr>
                            <tr>
                                <td className='td'>
                                    <button className='btn btn-primary'
                                            onClick={addRecord}>
                                        Submit
                                    </button>
                                </td>
                                <td className='td'>
                                    <button className='btn btn-danger'
                                            onClick={clearRecord}>
                                        Reset
                                    </button>
                                </td>
                            </tr>
                            <tr>
                                <td colSpan={"2"}  className='td'>
                                    <h6 style={{color: "orange"}}>
                                        {message}
                                    </h6>
                                    <br></br><br></br>
                                    <Link to={"/home"}> Go Home </Link> 
                                </td>
                            </tr>
                        </tbody>
                    </table>
               </center>
           </div>
}

export default AddSponsor;