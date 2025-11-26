import axios from "axios";
import React, { useState } from "react";
import { useEffect } from 'react';
import { Plus, Edit2, Trash2 } from 'lucide-react';
import api from "../api/api";

function TaskManager({ user, onLogout, successLogin }) {
  const [tasks, setTasks] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingTask, setEditingTask] = useState(null);
  const [newTask, setNewTask] = useState('');
  const [showMessage, setShowMessage]=useState(false);
  const totalTasks =  tasks.length;


  useEffect(() => {

    const fetchTasks = async() => {
      try{
        const response = await api.get("http://localhost:8080/api/tasks");
        setTasks(response.data);
      }
      catch(error){
        console.error("Error fetching tasks:", error);
      }
      
    };
    fetchTasks();


    if(successLogin){
      setShowMessage(true);

      setTimeout(() => setShowMessage(false), 2000);
    }

  },[successLogin])

  const handleKeyPress = (e) =>{
    if(e.key === 'Enter')
      handleSubmit();
  }

  const handleSubmit = async() => {
    if(!newTask.trim()) 
      return;

    const taskExist = tasks.some(task => task.title.toLowerCase() === newTask.toLowerCase())

    if(taskExist){
      alert('Task already exist!');
      setNewTask('')
      return;
    }

    try{
      if(editingTask){
        const updated = {...editingTask, title: newTask}

        const response = await api.put(`http://localhost:8080/api/tasks/${editingTask.id}`, updated)


        setTasks(tasks.map(task => task.id === editingTask.id ? response.data: task));
        setEditingTask(null)
      }else{

        const task={ 
          title: newTask,
          completed: false
        }
        const response = await api.post(`http://localhost:8080/api/tasks`, task)
        setTasks([...tasks,response.data])
      }
    }
    catch(error){
      console.error(error);
      alert(error)
    }
    setNewTask('')
    setShowForm(false)
  }

  const editTask =(task) =>{
    setEditingTask(task);
    setNewTask(task.title);
    setShowForm(true);
  }

  const deleteTask = async(taskToRemove) => {
    try{
      const response = await api.delete(`http://localhost:8080/api/tasks/${taskToRemove.id}`)
      const updatedTasks=tasks.filter(task => task.id!== taskToRemove.id)
      setTasks(updatedTasks)
      setNewTask('')
      setEditingTask('')
      setShowForm(false)
    }catch(error){
      console.error(error);
      alert(error)
    }
  }

  const completedTask =tasks.filter(task => task.completed).length


  const toggleTask = (taskChecked) =>{
    const updatedTasks = tasks.map(task => task.id === taskChecked.id ?
      {...task, completed: !taskChecked.completed} : task)
    setTasks(updatedTasks)
  }

  const cancel = () =>{
    setNewTask('')
    setEditingTask('')
    setShowForm(false)
  }


  return (
<div className="relative">
  {showMessage &&  (
      <div className="fixed top-5 left-1/2 -translate-x-1/2 
                    bg-green-600 text-white px-4 py-2 rounded-lg shadow-lg
                    animate-fade-in-out z-50">
      Login successful!
    </div>)}
  <h1 className="fixed top-5 left-1/2 -translate-x-1/2 
                   text-xl font-bold z-40" >Welcome, {user.name}</h1>

    <div className="min-h-screen bg-gray-100 p-8 flex items-center justify-center">
      <div className="flex flex-col bg-gray-100 border border-black-300 shadow-md p-4 rounded">
        <h1 className="text-[2vw] font-bold text-center">Task Tracker</h1>
        <div className="text-[1vw] text-center">{completedTask} of {totalTasks} completed</div>


        {!showForm ? (
    // showForm is False
          <>
          <div className="flex flex-row py-2 gap-2">
            <input type="text" placeholder="Add a new task ..." value=""
              className="text-[1vw] px-3 py-2 border border-gray-300 rounded flex-1 h-[2.5rem] focus:outline-none focus:border-blue-500"
              onClick={()=> setShowForm(true)} readOnly/>
              <button className="flex flex-row h-[2.5rem] text-[1vw] gap-2">
                <Plus  className="w-[2rem] h-full bg-blue-500 text-white rounded hover:bg-blue-600 " onClick={()=> setShowForm(true)}/>
                  Add
                </button> 
              </div>
              </>
              ) : (
     // showForm is true
              <>
              <div className="flex flex-row py-2 gap-2">
                <input type="text" placeholder="Add a new task ..." 
                  className="text-[1vw] px-3 py-2 border border-gray-300 rounded flex-1 h-[2.5rem] focus:outline-none focus:border-blue-500" value={newTask} 
                  onChange={(e) => setNewTask(e.target.value)}
                  onKeyPress={handleKeyPress} autoFocus/>
                  <button className="flex flex-row h-[1rem] text-[1vw] gap-2" onClick={handleSubmit}>
                    {editingTask ? 'Update' : 'Add'}
                  </button> 
                  <button className="flex flex-row h-[1rem] text-[1vw] gap-2" onClick={cancel}>
                    Cancel
                  </button>
                </div>
                </>
                )}

    {/*task List*/}

              <div>
                <>
                {tasks.length === 0 ?(
                  <div className="flex flex-row text-[1vw]">
                    No tasks yet. Please add more tasks.
                  </div>
                  ) :(tasks.map((task) => (
                    <div className="flex flex-row  items-center gap-1 border border-gray-300 rounded mb-2 p-2 text-[1vw] h-12">
                      <input type="checkbox" checked={task.completed} 
                        onChange ={() => toggleTask(task)} className="w-4 h-4 text-blue-600 rounded focus:ring-blue-500"/>
                        <p>{task.title}</p>
                        <button className="text-[1vw]" onClick={() => editTask(task)}>
                          <Edit2 size={15} /></button>
                          <button className="text-[1vw]" onClick={() => deleteTask(task)}>
                            <Trash2 size={15} /></button>
                          </div>

                          )))}
                  </>
                </div>

              </div>
            </div>
            </div>
            );
}

export default TaskManager;