import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import NoPage from "./pages/NoPage";
import Login from "./pages/Login";
import ReservationPage from "./pages/UserReservation";
import Goal from "./pages/Goal";
import GoalPage from "./pages/GoalPage";
import IncomePage from "./pages/IncomePage";
import IncomeDetailsPage from "./pages/IncomeDetailPage";
import DebtPage from "./pages/Debt";
import DebtDetailPage from "./pages/DebtPage";
import ExpensePage from "./pages/ExpensePage";
import ExpenseDetailPage from "./pages/ExpenseDetailPage";

export default function App() {
    return (
        <div>
            <BrowserRouter>
                <Routes>
                    <Route index element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/no-page" element={<NoPage />} />
                    <Route path="/goal" element={<Goal />} />
                    <Route path="/goal/:id" element={<GoalPage />} />
                    <Route path="/income" element={<IncomePage />} />
                    <Route path="/income/:id" element={<IncomeDetailsPage />} />
                    <Route path="/debt/:id" element={<DebtDetailPage />} />
                    <Route path="/expense/:id" element={<ExpenseDetailPage />} />


                    <Route path="/debt" element={<DebtPage />} />
                    <Route path="/expense" element={<ExpensePage />} />


                    <Route path="*" element={<NoPage />} />
                </Routes>
            </BrowserRouter>
        </div>
    )
}
