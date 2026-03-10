import { useEffect, useState } from "react";
import axios from "axios";
import Header from "../shared/Header";
const API = "http://localhost:8080/api";
type Conference = {
  id: number;
  title: string;
  location: string;
  description: string;
};
type Session = {
  id: number;
  title: string;
  track: string;
  capacity: number;
};
type Attendee = {
  id?: number;
  name: string;
  email: string;
};
const DashboardUser = () => {
  const [conferences, setConferences] = useState<Conference[]>([]);
  const [sessions, setSessions] = useState<Session[]>([]);
  const [selectedConference, setSelectedConference] = useState<number | null>(
    null,
  );
  const [selectedSession, setSelectedSession] = useState<number | null>(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [attendee, setAttendee] = useState<Attendee>({
    name: "",
    email: "",
  });
  const [attendeeId, setAttendeeId] = useState<number | null>(null);
  setAttendeeId;
  useEffect(() => {
    fetchConferences();
  }, [page]);
  const fetchConferences = async () => {
    const res = await axios.get(`${API}/conferences?page=${page}&size=5`);
    setConferences(res.data.content);
    setTotalPages(res.data.totalPages);
  };
  const fetchSessions = async (conferenceId: number) => {
    const res = await axios.get(`${API}/conferences/${conferenceId}/sessions`);
    setSessions(res.data);
  };
  const selectConference = (conf: Conference) => {
    setSelectedConference(conf.id);
    fetchSessions(conf.id);
  };
  const handleAttendeeChange = (e: any) => {
    setAttendee({
      ...attendee,
      [e.target.name]: e.target.value,
    });
  };
  const registerAttendee = async (e: any) => {
    e.preventDefault();
    if (!selectedSession) {
      alert("Please select a session first");
      return;
    }
    try {
      const res = await axios.post(
        `http://localhost:8080/api/sessions/${selectedSession}/attendees`,
        attendee,
      );
      setAttendeeId(res.data.id);
      alert("Attendee Registered Successfully");
    } catch (error) {
      console.error(error);
    }
  };
  const registerSession = async (sessionId: number) => {
    if (!attendeeId) {
      alert("Please register attendee first");
      return;
    }
    try {
      await axios.post(
        `http://localhost:8080/api/sessions/${sessionId}/${attendeeId}`,
      );
      alert("Session Registered Successfully");
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <div style={{ padding: "30px" }}>
      <Header />
      <h1>User Dashboard</h1>
      {/* Attendee Registration */}
      <h2>Register as Attendee</h2>
      <form onSubmit={registerAttendee}>
        <input
          name="name"
          placeholder="Name"
          value={attendee.name}
          onChange={handleAttendeeChange}
          required
        />
        <input
          name="email"
          placeholder="Email"
          value={attendee.email}
          onChange={handleAttendeeChange}
          required
        />
        <button type="submit">Register</button>
      </form>
      {/* Conferences */}
      <h2 style={{ marginTop: "40px" }}>Conferences</h2>
      <table border={1} cellPadding={10} width="100%">
        <thead>
          <tr>
            <th>Title</th>
            <th>Location</th>
          </tr>
        </thead>
        <tbody>
          {conferences.map((conf) => (
            <tr
              key={conf.id}
              onClick={() => selectConference(conf)}
              style={{ cursor: "pointer" }}
            >
              <td>{conf.title}</td>
              <td>{conf.location}</td>
            </tr>
          ))}
        </tbody>
      </table>
      {/* Pagination */}
      <div style={{ marginTop: 20 }}>
        <button onClick={() => setPage(page - 1)} disabled={page === 0}>
          Prev
        </button>
        <span style={{ margin: "0 10px" }}>
          Page {page + 1} of {totalPages}
        </span>
        <button
          onClick={() => setPage(page + 1)}
          disabled={page + 1 >= totalPages}
        >
          Next
        </button>
      </div>
      {/* Sessions */}
      {selectedConference && (
        <div style={{ marginTop: "40px" }}>
          <h2>Sessions</h2>
          <table border={1} cellPadding={10} width="100%">
            <thead>
              <tr>
                <th>Title</th>
                <th>Track</th>
                <th>Capacity</th>
                <th>Select</th>
                <th>Register</th>
              </tr>
            </thead>
            <tbody>
              {sessions.map((s) => (
                <tr key={s.id}>
                  <td>{s.title}</td>
                  <td>{s.track}</td>
                  <td>{s.capacity}</td>
                  <td>
                    <button
                      onClick={() => {
                        setSelectedSession(s.id);
                        alert("Session selected");
                      }}
                    >
                      Select
                    </button>
                  </td>
                  <td>
                    <button onClick={() => registerSession(s.id)}>
                      Register
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};
export default DashboardUser;
