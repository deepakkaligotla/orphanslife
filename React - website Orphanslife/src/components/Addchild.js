import React from 'react';

import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';



function AddChild()
{
    const [child, setChild] = useState({});
    const [message, setmessage] = useState("");
    const [shouldCleanTextBoxes, setShouldCleanTextBoxes] = useState(false);

    const handleChange = (args)=>
    {
        var copyOfCurrentChildInState = {...child};
        copyOfCurrentChildInState[args.target.name] = args.target.value;
        setChild(copyOfCurrentChildInState);
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
            setChild({No: 0, Name: "", Address: ""});
        }
    }, [shouldCleanTextBoxes])

    const addRecord =()=>
    {
        var helper = new XMLHttpRequest();
        helper.onreadystatechange = ()=>{
            if(helper.readyState === 1 && helper.status === 200)
            {
                var result = JSON.parse(helper.responseText);
                if(result.data.affectedRows!==undefined)
                {
                    if(result.data.affectedRows > 0)
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
        helper.open("POST","http://localhost:4000/newchild");
        helper.setRequestHeader("Content-Type", "application/json")
        helper.send(JSON.stringify(child));
    }

    const clearRecord =()=>
    {
        setChild({});
    }
    
    return (<React.Fragment>
        <div>
               <center>
                    <br></br>
                    <br></br>
                    <br></br>
                    <table>
                        <tbody>
                            <tr>
                                <td className='td'>Name</td>    
                                <td className='td'>
                                    <input type="text" name="name"
                                           value={child.name}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>DOB</td>    
                                <td className='td'>
                                    <input type="text" name="dob"
                                           value={child.dob}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Gender</td>    
                                <td className='td'>
                                    <input type="text" name="gender"
                                           value={child.gender}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Admitted Date</td>    
                                <td className='td'>
                                    <input type="text" name="admitted_date"
                                           value={child.admitted_date}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Leave Date</td>    
                                <td className='td'>
                                    <input type="text" name="leave_date"
                                           value={child.leave_date}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Mother Name</td>    
                                <td className='td'>
                                    <input type="text" name="mother_name"
                                           value={child.mother_name}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Father Name</td>    
                                <td className='td'>
                                    <input type="text" name="father_name"
                                           value={child.father_name}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Mobile</td>    
                                <td className='td'>
                                    <input type="text" name="mobile"
                                           value={child.mobile}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Child Image</td>    
                                <td className='td'>
                                    <input type="text" name="child_image"
                                           value={child.child_image}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Status ID</td>    
                                <td className='td'>
                                    <input type="text" name="status_id"
                                           value={child.status_id}
                                           onChange={handleChange}/>
                                </td>
                            </tr>

                            <tr>
                                <td className='td'>Admin ID</td>    
                                <td className='td'>
                                    <input type="text" name="admin_id"
                                           value={child.admin_id}
                                           onChange={handleChange}/>
                                </td>
                            </tr>
                            <tr>
                                <td className='td'>
                                    <button className='btn btn-danger'
                                            onClick={clearRecord}>
                                        Reset
                                    </button>
                                </td>
                                <td className='td'>
                                    <button className='btn btn-success'
                                            onClick={addRecord}>
                                        Submit
                                    </button>
                                </td>
                            </tr>
                            <h6 style={{color: "orange"}}>{message}</h6>
                            <tr>
                                <td colSpan={"2"}  className='td'>
                                    <h6 style={{color: "orange"}}>
                                        {message}
                                    </h6>
                                    <Link to={"/home"}> Go Home </Link>
                                </td>
                            </tr>
                        </tbody>
                    </table>
               </center>
           </div>
    </React.Fragment>)
}

export default AddChild;