import { useEffect, useState } from "react";
import axios from "axios";
import Header from "../shared/Header";
const API = "http://localhost:8080/api";
type Conference = {
  id?: string;
  title: string;
  location: string;
  description?: string;
};
type Session = {
  id?: string;
  title: string;
  description: string;
  track: string;
  capacity: number;
};
type Speaker = {
  id?: number;
  name: string;
  bio: string;
  photoUrl?: string;
};
const DashboardAdmin = () => {
  const [conferences, setConferences] = useState<Conference[]>([]);
  const [sessions, setSessions] = useState<Session[]>([]);
  const [selectedConference, setSelectedConference] = useState<string | null>(
    null,
  );
  const [showConferenceForm, setShowConferenceForm] = useState(false);
  const [showSessionForm, setShowSessionForm] = useState(false);
  const [totalPages, setTotalPages] = useState(0);
  const [editingConference, setEditingConference] = useState<Conference | null>(
    null,
  );
  const [conference, setConference] = useState({
    title: "",
    location: "",
    description: "",
  });
  const [session, setSession] = useState({
    title: "",
    description: "",
    track: "MAIN",
    capacity: 0,
  });
  const [page, setPage] = useState(0);
  const [speakers, setSpeakers] = useState<Speaker[]>([]);
  const [speaker, setSpeaker] = useState({
    name: "",
    bio: "",
  });
  const [photo, setPhoto] = useState<File | null>(null);
  const [showSpeakerForm, setShowSpeakerForm] = useState(false);
  useEffect(() => {
    fetchConferences();
  }, [page]);
  const fetchConferences = async () => {
    const res = await axios.get(
      `http://localhost:8080/api/conferences?page=${page}&size=5`,
    );
    setConferences(res.data.content);
    setTotalPages(res.data.totalPages);
  };
  const fetchSessions = async (id: string) => {
    const res = await axios.get(`${API}/conferences/${id}/sessions`);
    setSessions(res.data);
  };
  const handleSpeakerChange = (e: any) => {
    setSpeaker({
      ...speaker,
      [e.target.name]: e.target.value,
    });
  };
  const selectConference = (conf: Conference) => {
    setSelectedConference(conf.id!);
    fetchSessions(conf.id!);
  };
  const fetchSpeakers = async () => {
    const res = await axios.get(`${API}/speakers`);
    setSpeakers(res.data);
  };
  const saveSpeaker = async (e: any) => {
    e.preventDefault();
    const formData = new FormData();
    formData.append("name", speaker.name);
    formData.append("bio", speaker.bio);
    if (photo) {
      formData.append("photo", photo);
    }
    await axios.post("http://localhost:8080/api/speakers", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    setSpeaker({ name: "", bio: "" });
    setPhoto(null);
    setShowSpeakerForm(false);
    fetchSpeakers();
  };
  const deleteSpeaker = async (id?: number) => {
    if (!window.confirm("Delete this speaker?")) return;
    await axios.delete(`${API}/speakers/${id}`);
    fetchSpeakers();
  };
  const handleConferenceChange = (e: any) => {
    setConference({
      ...conference,
      [e.target.name]: e.target.value,
    });
  };
  const handleSessionChange = (e: any) => {
    setSession({
      ...session,
      [e.target.name]: e.target.value,
    });
  };
  const saveConference = async (e: any) => {
    e.preventDefault();
    if (editingConference) {
      await axios.put(`${API}/conferences/${editingConference.id}`, conference);
    } else {
      await axios.post(`${API}/conferences`, conference);
    }
    setConference({ title: "", location: "", description: "" });
    setShowConferenceForm(false);
    setEditingConference(null);
    fetchConferences();
  };
  const editConference = (conf: Conference) => {
    setConference({
      title: conf.title,
      location: conf.location,
      description: conf.description || "",
    });
    setEditingConference(conf);
    setShowConferenceForm(true);
  };
  const deleteConference = async (id?: string) => {
    if (!window.confirm("Delete this conference?")) return;
    await axios.delete(`${API}/conferences/${id}`);
    fetchConferences();
  };
  const saveSession = async (e: any) => {
    e.preventDefault();
    await axios.post(
      `${API}/conferences/${selectedConference}/sessions`,
      session,
    );
    setSession({
      title: "",
      description: "",
      track: "MAIN",
      capacity: 0,
    });
    setShowSessionForm(false);
    fetchSessions(selectedConference!);
  };
  const deleteSession = async (id?: string) => {
    if (!window.confirm("Delete this session?")) return;
    await axios.delete(`${API}/sessions/${id}`);
    fetchSessions(selectedConference!);
  };
  useEffect(() => {
    fetchConferences();
    fetchSpeakers();
  }, [page]);
  return (
    <div style={{ padding: "30px" }}>
      <Header />
      <h1>Admin Dashboard</h1>
      <h2>Conferences</h2>
      <button onClick={() => setShowConferenceForm(true)}>
        Create Conference
      </button>
      {showConferenceForm && (
        <form onSubmit={saveConference} style={{ marginTop: "20px" }}>
          <input
            name="title"
            placeholder="Title"
            value={conference.title}
            onChange={handleConferenceChange}
            required
          />
          <input
            name="location"
            placeholder="Location"
            value={conference.location}
            onChange={handleConferenceChange}
            required
          />
          <input
            name="description"
            placeholder="Description"
            value={conference.description}
            onChange={handleConferenceChange}
          />
          <button type="submit">Save</button>
          <button
            type="button"
            onClick={() => {
              setShowConferenceForm(false);
              setEditingConference(null);
            }}
          >
            Cancel
          </button>
        </form>
      )}
      <table border={1} cellPadding={10} width="100%" style={{ marginTop: 20 }}>
        <thead>
          <tr>
            <th>Title</th>
            <th>Location</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {conferences.map((conf) => (
            <tr key={conf.id}>
              <td
                onClick={() => selectConference(conf)}
                style={{ cursor: "pointer" }}
              >
                {conf.title}
              </td>
              <td>{conf.location}</td>
              <td>
                <button onClick={() => editConference(conf)}>Edit</button>
                <button onClick={() => deleteConference(conf.id)}>
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div style={{ marginTop: 20 }}>
        <button onClick={() => setPage(page - 1)} disabled={page === 0}>
          Prev
        </button>
        <span style={{ margin: "0 15px" }}>
          Page {page + 1} of {totalPages}
        </span>
        <button
          onClick={() => setPage(page + 1)}
          disabled={page + 1 >= totalPages}
        >
          Next
        </button>
      </div>
      {selectedConference && (
        <div style={{ marginTop: "40px" }}>
          <h2>Sessions</h2>
          <button onClick={() => setShowSessionForm(true)}>Add Session</button>
          {showSessionForm && (
            <form onSubmit={saveSession} style={{ marginTop: 20 }}>
              <input
                name="title"
                placeholder="Session Title"
                onChange={handleSessionChange}
                required
              />
              <input
                name="description"
                placeholder="Description"
                onChange={handleSessionChange}
              />
              <select name="track" onChange={handleSessionChange}>
                <option value="MAIN">MAIN</option>
                <option value="WORKSHOP">WORKSHOP</option>
                <option value="LIGHTNING">LIGHTNING</option>
              </select>
              <input
                type="number"
                name="capacity"
                placeholder="Capacity"
                onChange={handleSessionChange}
                required
              />
              <button type="submit">Save</button>
              <button type="button" onClick={() => setShowSessionForm(false)}>
                Cancel
              </button>
            </form>
          )}
          <table
            border={1}
            cellPadding={10}
            width="100%"
            style={{ marginTop: 20 }}
          >
            <thead>
              <tr>
                <th>Title</th>
                <th>Track</th>
                <th>Capacity</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {sessions.map((s) => (
                <tr key={s.id}>
                  <td>{s.title}</td>
                  <td>{s.track}</td>
                  <td>{s.capacity}</td>
                  <td>
                    <button onClick={() => deleteSession(s.id)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      <h2 style={{ marginTop: 40 }}>Speakers</h2>
      <button onClick={() => setShowSpeakerForm(true)}>Add Speaker</button>
      {showSpeakerForm && (
        <form onSubmit={saveSpeaker} style={{ marginTop: 20 }}>
          <input
            name="name"
            placeholder="Speaker Name"
            value={speaker.name}
            onChange={handleSpeakerChange}
            required
          />
          <input
            name="bio"
            placeholder="Bio"
            value={speaker.bio}
            onChange={handleSpeakerChange}
          />
          <input
            type="file"
            onChange={(e) => setPhoto(e.target.files?.[0] || null)}
          />
          <button type="submit">Save</button>
          <button type="button" onClick={() => setShowSpeakerForm(false)}>
            Cancel
          </button>
        </form>
      )}
      <table border={1} cellPadding={10} style={{ marginTop: 20 }}>
        <thead>
          <tr>
            <th>Name</th>
            <th>Bio</th>
            <th>Photo</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {speakers.map((sp) => (
            <tr key={sp.id}>
              <td>{sp.name}</td>
              <td>{sp.bio}</td>
              <td>
                {sp.photoUrl && (
                  <img
                    src={`http://localhost:8080/${sp.photoUrl}`}
                    width="60"
                    alt="speaker"
                  />
                )}
              </td>
              <td>
                <button onClick={() => deleteSpeaker(sp.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};
export default DashboardAdmin;
