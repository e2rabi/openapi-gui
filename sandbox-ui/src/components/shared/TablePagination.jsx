import {
    ChevronLeftIcon,
    ChevronRightIcon,
    DoubleArrowLeftIcon,
    DoubleArrowRightIcon,
} from "@radix-ui/react-icons"
import { Button } from "../ui/button"
const TablePagination = ({ pageInfo, changePage }) => {
    const nextPage = () => {
        changePage({
            ...pageInfo,
            pageNumber: pageInfo.pageNumber + 1
        })
    }
    const previousPage = () => {
        if (pageInfo.pageNumber > 0) {
            changePage({
                ...pageInfo,
                pageNumber: pageInfo.pageNumber - 1
            })
        }
    }
    const goToFirstPage = () => {
        if (pageInfo.pageNumber > 0) {
            changePage({
                ...pageInfo,
                pageNumber: 0
            })
        }
    }
    const goToLastPage = () => {
        if (pageInfo.totalPages > 0) {
            changePage({
                ...pageInfo,
                pageNumber: pageInfo.totalPages - 1
            })
        }
    }

    return (
        <>
            <div className="text-xs text-muted-foreground">
                Showing <strong>1-{pageInfo.pageSize}</strong> of <strong>{pageInfo.totalElements}</strong>{" "}
                users
            </div>
            <div className="flex items-center space-x-2">
                <Button
                    variant="outline"
                    className="hidden h-8 w-8 p-0 lg:flex"
                    onClick={() => goToFirstPage()}
                >
                    <span className="sr-only">Go to first page</span>
                    <DoubleArrowLeftIcon className="h-4 w-4" />
                </Button>
                <Button
                    variant="outline"
                    className="h-8 w-8 p-0"
                    onClick={() => previousPage()}
                    disabled={pageInfo.pageNumber == 0}
                >
                    <span className="sr-only">Go to previous page</span>
                    <ChevronLeftIcon className="h-4 w-4" />
                </Button>
                <Button
                    variant="outline"
                    className="h-8 w-8 p-0"
                    onClick={() => nextPage()}
                    disabled={(pageInfo.pageNumber + 1) == pageInfo.totalPages}
                >
                    <span className="sr-only">Go to next page</span>
                    <ChevronRightIcon className="h-4 w-4" />
                </Button>
                <Button
                    variant="outline"
                    className="hidden h-8 w-8 p-0 lg:flex"
                    onClick={() => goToLastPage()}
                >
                    <span className="sr-only">Go to last page</span>
                    <DoubleArrowRightIcon className="h-4 w-4" />
                </Button>
            </div>
        </>
    )
}

export default TablePagination
